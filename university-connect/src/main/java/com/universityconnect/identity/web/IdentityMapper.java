package com.universityconnect.identity.web;

import com.universityconnect.identity.DTOs.RosterEntryResponse;
import com.universityconnect.identity.DTOs.UserResponse;
import com.universityconnect.identity.Entity.RosterEntry;
import com.universityconnect.identity.Entity.User;

/** Hand-written mapping: explicit, easy to debug, no extra library. */
final class IdentityMapper {

    private IdentityMapper() {
    }

    static UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getUsn(), u.getEmail(), u.getAccountType(),
                u.getStatus(), u.getEmailVerifiedAt(), u.getCreatedAt());
    }

    static RosterEntryResponse toResponse(RosterEntry r) {
        return new RosterEntryResponse(r.getId(), r.getUsn(), r.getDepartment(),
                r.getAdmissionYear(), r.getGraduationYear());
    }
}
