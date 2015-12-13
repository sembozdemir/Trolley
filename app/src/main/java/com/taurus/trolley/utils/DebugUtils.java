package com.taurus.trolley.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by semih on 13.12.2015.
 */
public class DebugUtils {
    private static final String DEBUG_PREFS = "debug_prefs";
    private static final String KEY_USER_NUMBER = "user_number";
    private static final String TEST_PASSWORD = "test";
    private static final String TEST_USERNAME = "testUser";
    private static final String SUFFIX_EMAIL = "@test.com";
    private static Context context;

    public static DebugUser newUser() {
        SharedPreferences sp = context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int userNumber = sp.getInt(KEY_USER_NUMBER, 0);
        String username = TEST_USERNAME + userNumber;
        String email = username + SUFFIX_EMAIL;
        String password = TEST_PASSWORD;
        editor.putInt(KEY_USER_NUMBER, ++userNumber);
        editor.apply();

        return new DebugUser(username, email, password);
    }

    public static void init(Context contextParam) {
        context = contextParam;
    }

    public static class DebugUser {
        public final String username;
        public final String password;
        public final String email;

        public DebugUser(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }
}
