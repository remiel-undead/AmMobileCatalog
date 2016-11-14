package com.example.undead.ammobilecatalog.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {
    private static final String LOGIN_PREFS = "login_prefs";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_PASSWORD = "psswrd";

    public static String getSharedPrefLogin(Context context) {
        mockSharedPrefsLoginPasswordEncrypted(context);
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFS, context.MODE_PRIVATE);
        return preferences.getString(TAG_LOGIN, null);
    }

    public static String getSharedPrefPasswordEncrypted(Context context) {
        mockSharedPrefsLoginPasswordEncrypted(context);
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFS, context.MODE_PRIVATE);
        return preferences.getString(TAG_PASSWORD, null);
    }

    private static void mockSharedPrefsLoginPasswordEncrypted(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFS, context.MODE_PRIVATE);
        String login = preferences.getString(TAG_LOGIN, null);
        String passwordEncrypted = preferences.getString(TAG_PASSWORD, null);
        if (ObjectUtils.isEmpty(login) || ObjectUtils.isEmpty(passwordEncrypted)) {
            // TODO get login and password from dialog
            /**
             * Mock for getting login and password
             */
            login = "loginarea";
            passwordEncrypted = CryptUtils.encrypt("passarea");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TAG_LOGIN, login);
            editor.putString(TAG_PASSWORD, passwordEncrypted);
            editor.apply();
        }
    }
}
