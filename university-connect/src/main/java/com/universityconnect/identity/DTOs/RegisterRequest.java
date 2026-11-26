package com.universityconnect.identity.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.universityconnect.identity.api.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "USN is required")
        @Pattern(regexp = "^[A-Za-z0-9]{6,20}$", message = "USN must be 6-20 letters or digits")
        String usn,

        @Email(message = "Invalid email format")
        @Size(max = 255)
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be 8-72 characters")
        @JsonAlias({"initialPassword", "password"})
        String password,

        AccountType accountType) {
}
