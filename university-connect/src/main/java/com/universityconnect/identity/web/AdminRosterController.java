package com.universityconnect.identity.web;

import com.universityconnect.identity.DTOs.RosterEntryRequest;
import com.universityconnect.identity.DTOs.RosterEntryResponse;
import com.universityconnect.identity.application.RosterService;
import com.universityconnect.identity.Entity.RosterEntry;
import com.universityconnect.shared.pagination.PageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/admin/roster")
public class AdminRosterController {

    private final RosterService rosterService;

    public AdminRosterController(RosterService rosterService) {
        this.rosterService = rosterService;
    }

    @PostMapping
    public ResponseEntity<RosterEntryResponse> create(@Valid @RequestBody RosterEntryRequest request) {
        RosterEntry entry = rosterService.create(request.usn(), request.department(),
                request.admissionYear(), request.graduationYear());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(entry.getId()).toUri();
        return ResponseEntity.created(location).body(IdentityMapper.toResponse(entry));
    }

    @GetMapping("/{id}")
    public RosterEntryResponse get(@PathVariable Long id) {
        return IdentityMapper.toResponse(rosterService.getById(id));
    }

    @GetMapping
    public PageResponse<RosterEntryResponse> list(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        return PageResponse.from(rosterService.list(page, size), IdentityMapper::toResponse);
    }

    @PutMapping("/{id}")
    public RosterEntryResponse update(@PathVariable Long id,
                                      @Valid @RequestBody UpdateRosterEntryRequest request) {
        return IdentityMapper.toResponse(rosterService.update(id, request.department(),
                request.admissionYear(), request.graduationYear()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rosterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
