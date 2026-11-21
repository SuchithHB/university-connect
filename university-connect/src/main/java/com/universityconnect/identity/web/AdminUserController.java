package com.universityconnect.identity.web;

import com.universityconnect.identity.DTOs.UserResponse;
import com.universityconnect.identity.api.AccountStatus;
import com.universityconnect.identity.api.AccountType;
import com.universityconnect.identity.application.UserService;
import com.universityconnect.identity.Entity.User;
import com.universityconnect.shared.pagination.PageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Admin-only once security is added (path is under /admin).
 * There is intentionally no DELETE: accounts are deactivated via status (SUSPENDED),
 * because users own content in many other modules.
 */
@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.create(request.usn(), request.email(),
                request.initialPassword(), request.accountType());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(IdentityMapper.toResponse(user));
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        return IdentityMapper.toResponse(userService.getById(id));
    }

    @GetMapping
    public PageResponse<UserResponse> list(@RequestParam(required = false) AccountType accountType,
                                           @RequestParam(required = false) AccountStatus status,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return PageResponse.from(userService.search(accountType, status, page, size),
                IdentityMapper::toResponse);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return IdentityMapper.toResponse(userService.updateEmail(id, request.email()));
    }

    @PatchMapping("/{id}/status")
    public UserResponse changeStatus(@PathVariable Long id,
                                     @Valid @RequestBody UpdateUserStatusRequest request) {
        return IdentityMapper.toResponse(userService.changeStatus(id, request.status()));
    }
}
