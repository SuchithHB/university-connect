package com.universityconnect.identity.api;

/** Minimal, immutable view of a user that other modules may see. */
public record UserSummary(Long id, String usn, AccountType accountType, AccountStatus status) {
}
