package com.example.transportsystem.factory;

final class Strings {

    private Strings() {
    }

    static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
