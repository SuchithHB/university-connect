package com.universityconnect.identity.application;

import com.universityconnect.identity.api.AccountStatus;
import com.universityconnect.identity.api.IdentityApi;
import com.universityconnect.identity.api.UserSummary;
import com.universityconnect.identity.Entity.User;
import com.universityconnect.identity.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
class IdentityApiImpl implements IdentityApi {

    private final UserRepository users;

    IdentityApiImpl(UserRepository users) {
        this.users = users;
    }

    @Override
    public Optional<UserSummary> findSummary(Long userId) {
        return users.findById(userId).map(IdentityApiImpl::toSummary);
    }

    @Override
    public Map<Long, UserSummary> findSummaries(Collection<Long> userIds) {
        return users.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, IdentityApiImpl::toSummary));
    }

    @Override
    public boolean isActive(Long userId) {
        return users.findById(userId)
                .map(u -> u.getStatus() == AccountStatus.ACTIVE)
                .orElse(false);
    }

    private static UserSummary toSummary(User u) {
        return new UserSummary(u.getId(), u.getUsn(), u.getAccountType(), u.getStatus());
    }
}
