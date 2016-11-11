package com.example.undead.ammobilecatalog.utils;

import android.util.Base64;

public class CryptUtils {
    public static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}
