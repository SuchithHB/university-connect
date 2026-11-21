package com.universityconnect.identity.DTOs;

public record RosterEntryResponse(
        Long id,
        String usn,
        String department,
        Integer admissionYear,
        Integer graduationYear) {
}
