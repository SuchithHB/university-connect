package com.universityconnect.identity.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** No usn field: the USN is the identifier and cannot be changed. */
public record UpdateRosterEntryRequest(
        @NotBlank @Size(max = 100)
        String department,

        @NotNull @Min(2000) @Max(2100)
        Integer admissionYear,

        @Min(2000) @Max(2100)
        Integer graduationYear) {
}
