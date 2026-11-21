package com.universityconnect.identity.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RosterEntryRequest(
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9]{6,20}$", message = "USN must be 6-20 letters or digits")
        String usn,

        @NotBlank @Size(max = 100)
        String department,

        @NotNull @Min(2000) @Max(2100)
        Integer admissionYear,

        @Min(2000) @Max(2100)
        Integer graduationYear) {
}
