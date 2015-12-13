package com.taurus.trolley.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.taurus.trolley.App;
import com.taurus.trolley.OfferActivity;
import com.taurus.trolley.R;
import com.taurus.trolley.busevents.NewOfferEvent;
import com.taurus.trolley.domain.Offer;
import com.taurus.trolley.domain.OfferHistory;
import com.taurus.trolley.domain.User;
import com.taurus.trolley.helper.ParseQueryHelper;
import com.taurus.trolley.utils.BeaconPool;
import com.taurus.trolley.utils.Utility;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import java.util.Collection;
import java.util.List;

/**
 * Created by semih on 13.11.2015.
 */
public class BeaconDiscoverer extends Service implements BeaconConsumer {
    private static final String TAG = BeaconDiscoverer.class.getSimpleName();
    private static final int SCAN_PERIOD_SECOND = 2;
    private static final int BETWEEN_SCAN_PERIOD_SECOND = 25; // todo: it might be changed later
    private static BeaconManager beaconManager;
    private static Region region;
    private BackgroundPowerSaver backgroundPowerSaver;

    public BeaconDiscoverer() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        region = new Region("myRangingUniqueId", null, null, null);
        beaconManager = BeaconManager.getInstanceForApplication(getApplicationContext());

        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        configureBatterySaverMode();

        // todo: it will be deleted, clear pool for debugging purpose
        // BeaconPool.getPool().clear();

        beaconManager.bind(this);
    }

    @Override
    public void onDestroy() {
        beaconManager.unbind(this);
        super.onDestroy();
    }

    private void configureBatterySaverMode() {
        BeaconManager.setAndroidLScanningDisabled(true);
        backgroundPowerSaver = new BackgroundPowerSaver(getApplicationContext());

        // set the duration of the scan
        beaconManager.setBackgroundScanPeriod(Utility.convertToMilliseconds(SCAN_PERIOD_SECOND));
        // set the time between each scan
        beaconManager.setBackgroundBetweenScanPeriod(Utility.convertToMilliseconds(BETWEEN_SCAN_PERIOD_SECOND));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "BeaconDiscoverer started up");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onBeaconServiceConnect() {
        Log.d(TAG, "onBeaconServiceConnect");
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Beacon firstBeacon = beacons.iterator().next();
                    Log.i(TAG, "Beacon detected: " + firstBeacon.getDistance() + " m. - " + firstBeacon.getBluetoothAddress());

                    // if beacon has never been detected before
                    if (!BeaconPool.getPool().contains(firstBeacon)) {
                        BeaconPool.getPool().add(firstBeacon);
                        // check Beacon
                        ParseQueryHelper.getOfferFromBeacon(firstBeacon.getBluetoothAddress(), new ParseQueryHelper.Callback() {
                            @Override
                            public void done(List results, ParseException e) {
                                if (e == null) {
                                    final Offer offer = (Offer) results.get(ParseQueryHelper.OFFER_INDEX);
                                    Log.d(TAG, "New Offer: " + offer.getDescription());

                                    final OfferHistory offerHistory = new OfferHistory(offer, User.currentUser(), false);
                                    offerHistory.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                issueNotification(offer);
                                                sendOfferEvent(offerHistory);
                                            } else {
                                                Log.e(TAG, e.getMessage());
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });

        startRanging();
    }

    private void sendOfferEvent(OfferHistory offerHistory) {
        App.bus.post(new NewOfferEvent(offerHistory));
    }

    private void issueNotification(Offer offer) {
        String imageUrl = offer.getOfferImageUrl();
        // First of all, fetch the image and cache in the memory to get ready to show it later.
        Picasso.with(this).load(imageUrl).fetch();

        // create resultIntent to launch OfferActivity
        Intent resultIntent = OfferActivity.newIntent(this, offer);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        BeaconDiscoverer.this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // create placeholder notification icon bitmap
        Bitmap placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bag);

        // create builder to build notification
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(BeaconDiscoverer.this);
        builder.setContentTitle("Trolley Fırsatı")
                .setContentText(offer.getDescription())
                .setSmallIcon(R.drawable.ic_bag_greyscale)
                .setAutoCancel(true)
                .setLargeIcon(placeholder)
                .setDefaults(0) // so that it does not ring twice
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(offer.getDescription())) // so that it can be expandable
                .setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        final int notificationId = 001;
        // Gets an instance of the NotificationManager service
        final NotificationManager notifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Picasso.with(this).load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                builder.setLargeIcon(bitmap)
                        .setDefaults(Notification.DEFAULT_ALL);

                // Builds the notification and issues it again, but this time with the brand logo.
                notifyMgr.notify(notificationId, builder.build());
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        // Builds the notification and issues it.
        notifyMgr.notify(notificationId, builder.build());
    }

    public void stopRanging() {
        try {
            beaconManager.stopRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void startRanging() {
        if (User.currentUser() == null)
            return;

        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
