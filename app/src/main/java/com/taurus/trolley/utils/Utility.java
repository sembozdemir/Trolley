package com.taurus.trolley.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by semih on 16.11.2015.
 */
public class Utility {
    private static final String REQ_ANDROID_4_3 = "requires Android 4.3";
    private static final String REQ_BLE = "requires Bluetooth LE";

    public static boolean isPreLollipop() {
        return android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    public static long convertToMilliseconds(int seconds) {
        return seconds * 1000;
    }

    public String getIncompatibilityReason(Context context) {
        if (android.os.Build.VERSION.SDK_INT < 18) {
            return REQ_ANDROID_4_3;
        }
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return REQ_BLE;
        }
        return null;
    }
}
