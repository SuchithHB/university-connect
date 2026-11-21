package com.universityconnect.identity.web;

import com.universityconnect.identity.api.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9]{6,20}$", message = "USN must be 6-20 letters or digits")
        String usn,

        @Email @Size(max = 255)
        String email,

        // BCrypt only uses the first 72 bytes, hence the upper bound.
        @NotBlank @Size(min = 8, max = 72, message = "Password must be 8-72 characters")
        String initialPassword,

        @NotNull
        AccountType accountType) {
}
