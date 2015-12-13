package com.taurus.trolley;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.squareup.otto.Subscribe;
import com.taurus.trolley.busevents.BluetoothEvent;
import com.taurus.trolley.busevents.CouldNotGetLocationEvent;
import com.taurus.trolley.domain.User;
import com.taurus.trolley.helper.BluetoothStateHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout coordinatorLayout;
    private BluetoothStateHelper bluetoothStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        bluetoothStateReceiver = new BluetoothStateHelper();

        final User currentUser = User.currentUser();
        if (currentUser != null) {
            // do stuff with the user
            if (!BluetoothStateHelper.isBluetoothEnabled()) {
                handleBluetoothStateOff();
            }

            // To see dummy datas on Parse.com. It should be invoked only once for same data.
            // DummyData.setDummiesForParse();
            // DummyData.addNewShops();
        } else {
            // show the signup or login screen
            // stopService(new Intent(this, BeaconDiscoverer.class));
            redirectToLoginActivity();
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.bus.register(this);
    }

    @Override
    protected void onPause() {
        App.bus.unregister(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluetoothStateReceiver.isRegistered()) {
            unregisterReceiver(bluetoothStateReceiver);
            bluetoothStateReceiver.setRegistered(false);
        }
    }

    @Subscribe
    public void couldNotGetLocationEventReceived(CouldNotGetLocationEvent event) {
        String message = "Please turn on your location services to see nearby shops";
        Log.i(TAG, message);
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setText(message)
                .setAction("TURN ON", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).show();
    }

    @Subscribe
    public void bluetoothEventReceived(BluetoothEvent event) {
        switch (event.state) {
            case BluetoothAdapter.STATE_OFF:
                handleBluetoothStateOff();
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                Log.i(TAG, "Turning Bluetooth off...");
                break;
            case BluetoothAdapter.STATE_ON:
                handleBluetoothStateOn();
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                handleBluetoothStateTurningOn();
                break;
        }
    }

    private void handleBluetoothStateTurningOn() {
        String message = "Bluetooth Turning On";
        Log.i(TAG, message);
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT)
                .setText(message).show();
    }

    private void handleBluetoothStateOn() {
        String message = "Bluetooth On";
        Log.i(TAG, message);
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setText(message).show();
    }

    private void handleBluetoothStateOff() {
        String message = "Bluetooth Off";
        Log.i(TAG, message);
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setText(message)
                .setAction("TURN ON", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BluetoothStateHelper.turnOnBluetooth();
                    }
                }).show();
    }

    private void redirectToLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void keyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.taurus.trolley",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
