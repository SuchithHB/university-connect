package com.universityconnect.identity.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/** Only the email is editable here. A null/blank email clears it. */
public record UpdateUserRequest(
        @Email @Size(max = 255)
        String email) {
}
