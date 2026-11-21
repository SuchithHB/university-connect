package com.universityconnect.identity.Entity;

import com.universityconnect.shared.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Official university list of USNs. Used later to verify who may belong on the platform. */
@Entity
@Table(name = "university_roster",
        uniqueConstraints = @UniqueConstraint(name = "uk_roster_usn", columnNames = "usn"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RosterEntry extends BaseEntity {

    @Column(name = "usn", nullable = false, length = 20)
    private String usn;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Column(name = "admission_year", nullable = false)
    private Integer admissionYear;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    public RosterEntry(String usn, String department, Integer admissionYear, Integer graduationYear) {
        this.usn = usn;
        this.department = department;
        this.admissionYear = admissionYear;
        this.graduationYear = graduationYear;
    }

    // The USN is the identifier and is intentionally not updatable.
    public void update(String department, Integer admissionYear, Integer graduationYear) {
        this.department = department;
        this.admissionYear = admissionYear;
        this.graduationYear = graduationYear;
    }
}
