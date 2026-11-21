package com.universityconnect.identity.application;

import java.util.Locale;

/** Keeps stored values canonical so unique constraints and lookups behave predictably. */
final class Normalizer {

    private Normalizer() {
    }

    static String usn(String raw) {
        return raw.trim().toUpperCase(Locale.ROOT);
    }

    /** Blank becomes null so several users without email do not clash. */
    static String email(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        return raw.trim().toLowerCase(Locale.ROOT);
    }
}
