package com.universityconnect.identity.DTOs;

import com.universityconnect.identity.api.AccountStatus;
import com.universityconnect.identity.api.AccountType;

import java.time.Instant;

/** Deliberately has no password or hash field. */
public record UserResponse(
        Long id,
        String usn,
        String email,
        AccountType accountType,
        AccountStatus status,
        Instant emailVerifiedAt,
        Instant createdAt) {
}
