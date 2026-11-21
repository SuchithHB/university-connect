package com.universityconnect.identity.application;

import com.universityconnect.identity.Entity.RosterEntry;
import com.universityconnect.identity.Repository.RosterEntryRepository;
import com.universityconnect.shared.error.BusinessRuleException;
import com.universityconnect.shared.error.ConflictException;
import com.universityconnect.shared.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RosterService {

    private static final int MAX_PAGE_SIZE = 100;

    private final RosterEntryRepository roster;

    public RosterService(RosterEntryRepository roster) {
        this.roster = roster;
    }

    @Transactional
    public RosterEntry create(String usn, String department, Integer admissionYear, Integer graduationYear) {
        String normalizedUsn = Normalizer.usn(usn);
        checkYears(admissionYear, graduationYear);
        if (roster.existsByUsn(normalizedUsn)) {
            throw new ConflictException("ROSTER_USN_ALREADY_EXISTS", "This USN is already in the roster.");
        }
        return roster.save(new RosterEntry(normalizedUsn, department.trim(), admissionYear, graduationYear));
    }

    public RosterEntry getById(Long id) {
        return roster.findById(id)
                .orElseThrow(() -> new NotFoundException("ROSTER_ENTRY_NOT_FOUND",
                        "Roster entry " + id + " not found."));
    }

    public Page<RosterEntry> list(int page, int size) {
        return roster.findAll(PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), MAX_PAGE_SIZE),
                Sort.by("usn")));
    }

    @Transactional
    public RosterEntry update(Long id, String department, Integer admissionYear, Integer graduationYear) {
        RosterEntry entry = getById(id);
        checkYears(admissionYear, graduationYear);
        entry.update(department.trim(), admissionYear, graduationYear);
        return entry;
    }

    @Transactional
    public void delete(Long id) {
        roster.delete(getById(id)); // roster rows have no dependants, so a hard delete is safe
    }

    private void checkYears(Integer admissionYear, Integer graduationYear) {
        if (graduationYear != null && graduationYear < admissionYear) {
            throw new BusinessRuleException("INVALID_YEARS",
                    "Graduation year cannot be earlier than admission year.");
        }
    }
}
