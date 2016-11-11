package com.example.undead.ammobilecatalog.utils;

import java.util.Collection;

public class ObjectUtils {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}