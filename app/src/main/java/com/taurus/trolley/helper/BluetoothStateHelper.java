package com.taurus.trolley.helper;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.taurus.trolley.App;
import com.taurus.trolley.busevents.BluetoothEvent;

public class BluetoothStateHelper extends BroadcastReceiver {
    private static final String TAG = BluetoothStateHelper.class.getSimpleName();

    private boolean isRegistered;

    public BluetoothStateHelper() {
        isRegistered = false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        isRegistered = true;

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            sendBluetoothEvent(state);
        }
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    private void sendBluetoothEvent(int state) {
        App.bus.post(new BluetoothEvent(state));
    }

    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }

    public static void turnOnBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }
}
