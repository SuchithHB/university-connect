package com.universityconnect.identity.Repository;

import com.universityconnect.identity.Entity.RosterEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RosterEntryRepository extends JpaRepository<RosterEntry, Long> {

    Optional<RosterEntry> findByUsn(String usn);

    boolean existsByUsn(String usn);
}
