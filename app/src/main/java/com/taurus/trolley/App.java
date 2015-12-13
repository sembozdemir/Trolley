package com.taurus.trolley;

import android.app.Application;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.taurus.trolley.domain.Badge;
import com.taurus.trolley.domain.Beacon;
import com.taurus.trolley.domain.Brand;
import com.taurus.trolley.domain.Offer;
import com.taurus.trolley.domain.OfferHistory;
import com.taurus.trolley.domain.Shelf;
import com.taurus.trolley.domain.Shop;
import com.taurus.trolley.domain.Transaction;
import com.taurus.trolley.domain.User;
import com.taurus.trolley.services.BeaconDiscoverer;
import com.taurus.trolley.utils.DebugUtils;

/**
 * Created by semih on 08.11.2015.
 */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    public static Bus bus = new Bus(ThreadEnforcer.ANY);

    @Override
    public void onCreate() {
        super.onCreate();

        initFacebook();
        initParse();
        initHawk();

        DebugUtils.init(this);

        startService(new Intent(this, BeaconDiscoverer.class));
    }

    private void initFacebook() {
        // Facebook SDK initializing
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    private void initParse() {
        ParseUser.registerSubclass(User.class);
        ParseObject.registerSubclass(Brand.class);
        ParseObject.registerSubclass(Beacon.class);
        ParseObject.registerSubclass(Shop.class);
        ParseObject.registerSubclass(Offer.class);
        ParseObject.registerSubclass(Shelf.class);
        ParseObject.registerSubclass(Badge.class);
        ParseObject.registerSubclass(OfferHistory.class);
        ParseObject.registerSubclass(Transaction.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "xS10JGDdNnFE0vb4yaHEkMrBcczWCKAu1yCdw5fD", "CUjI6Hs7GZmKpMMdcYGUjb3TEkTtMI6Xy15IkFfn");
        ParseFacebookUtils.initialize(this);
    }

    private void initHawk() {
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(LogLevel.FULL)
                .setCallback(new HawkBuilder.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                })
                .build();
    }
}
