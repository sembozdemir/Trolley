package com.taurus.trolley.utils;

import android.os.Build;

/**
 * Created by semih on 16.11.2015.
 */
public class Utility {
    public static boolean isPreLollipop() {
        return android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }
}
