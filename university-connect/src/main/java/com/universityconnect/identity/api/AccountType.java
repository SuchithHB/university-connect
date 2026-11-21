package com.universityconnect.identity.api;

/** In the api package because other modules need it (e.g. feed: "only ALUMNI may post"). */
public enum AccountType {
    STUDENT, ALUMNI, ADMIN
}
