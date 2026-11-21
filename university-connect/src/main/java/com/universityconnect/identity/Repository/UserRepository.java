package com.universityconnect.identity.Repository;

import com.universityconnect.identity.api.AccountStatus;
import com.universityconnect.identity.api.AccountType;
import com.universityconnect.identity.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsn(String usn);

    boolean existsByUsn(String usn);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    @Query("""
            select u from User u
            where (:type is null or u.accountType = :type)
              and (:status is null or u.status = :status)
            """)
    Page<User> search(@Param("type") AccountType type,
                      @Param("status") AccountStatus status,
                      Pageable pageable);
}
