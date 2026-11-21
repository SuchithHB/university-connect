package com.universityconnect.identity.api;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/** The ONLY identity types other modules may depend on. */
public interface IdentityApi {

    Optional<UserSummary> findSummary(Long userId);

    /** Batch lookup to avoid N+1 calls (e.g. author names for a page of posts). */
    Map<Long, UserSummary> findSummaries(Collection<Long> userIds);

    boolean isActive(Long userId);
}
