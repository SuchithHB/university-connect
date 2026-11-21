package com.universityconnect.identity.web;

import com.universityconnect.identity.api.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(@NotNull AccountStatus status) {
}
