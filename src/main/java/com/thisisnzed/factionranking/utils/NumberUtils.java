package com.thisisnzed.factionranking.utils;

public class NumberUtils {

    public static boolean isInteger(final String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (final NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isDouble(final String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (final NumberFormatException ignored) {
            return false;
        }
    }
}
