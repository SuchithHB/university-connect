package com.universityconnect.identity.Entity;

import com.universityconnect.identity.api.AccountStatus;
import com.universityconnect.identity.api.AccountType;
import com.universityconnect.shared.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/** Account + credentials only. Names and academic data live in the profile module. */
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_usn", columnNames = "usn"),
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        },
        indexes = @Index(name = "idx_users_type_status", columnList = "account_type, status"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    /** University Seat Number, stored upper-case. Login identifier. */
    @Column(name = "usn", nullable = false, length = 20)
    private String usn;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status;

    @Column(name = "email_verified_at")
    private Instant emailVerifiedAt;

    public User(String usn, String email, String passwordHash, AccountType accountType) {
        this.usn = usn;
        this.email = email;
        this.passwordHash = passwordHash;
        this.accountType = accountType;
        this.status = AccountStatus.ACTIVE;
    }

    // Intention-revealing mutators instead of public setters: only these fields may change.
    public void changeEmail(String email) {
        this.email = email;
        this.emailVerifiedAt = null;
    }

    public void changeStatus(AccountStatus status) {
        this.status = status;
    }

    public void changePasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
