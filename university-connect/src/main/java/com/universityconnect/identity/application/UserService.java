package com.universityconnect.identity.application;

import com.universityconnect.identity.api.AccountStatus;
import com.universityconnect.identity.api.AccountType;
import com.universityconnect.identity.Entity.User;
import com.universityconnect.identity.Repository.UserRepository;
import com.universityconnect.shared.error.BusinessRuleException;
import com.universityconnect.shared.error.ConflictException;
import com.universityconnect.shared.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private static final int MAX_PAGE_SIZE = 100;

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    /** Admin provisioning. Public self-registration is intentionally not part of this stage. */
    @Transactional
    public User create(String usn, String email, String rawPassword, AccountType accountType) {
        // Admins are created directly in the database, never through an API.
        if (accountType == AccountType.ADMIN) {
            throw new BusinessRuleException("ADMIN_CREATION_NOT_ALLOWED",
                    "Admin accounts cannot be created through the API.");
        }
        String normalizedUsn = Normalizer.usn(usn);
        String normalizedEmail = Normalizer.email(email);

        if (users.existsByUsn(normalizedUsn)) {
            throw new ConflictException("USN_ALREADY_EXISTS", "A user with this USN already exists.");
        }
        if (normalizedEmail != null && users.existsByEmail(normalizedEmail)) {
            throw new ConflictException("EMAIL_ALREADY_EXISTS", "A user with this email already exists.");
        }
        User user = new User(normalizedUsn, normalizedEmail, passwordEncoder.encode(rawPassword), accountType);
        return users.save(user);
    }

    public User getById(Long id) {
        return users.findById(id)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User " + id + " not found."));
    }

    public Page<User> search(AccountType type, AccountStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), MAX_PAGE_SIZE),
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return users.search(type, status, pageable);
    }

    @Transactional
    public User updateEmail(Long id, String email) {
        User user = getById(id);
        String normalizedEmail = Normalizer.email(email);
        if (normalizedEmail != null && users.existsByEmailAndIdNot(normalizedEmail, id)) {
            throw new ConflictException("EMAIL_ALREADY_EXISTS", "A user with this email already exists.");
        }
        user.changeEmail(normalizedEmail);
        return user; // managed entity: flushed on commit
    }

    @Transactional
    public User changeStatus(Long id, AccountStatus status) {
        User user = getById(id);
        user.changeStatus(status);
        return user;
    }
}
