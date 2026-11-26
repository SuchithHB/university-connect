package com.universityconnect.identity.application;

import com.universityconnect.identity.DTOs.LoginResponse;
import com.universityconnect.identity.DTOs.RegisterRequest;
import com.universityconnect.identity.web.CreateUserRequest;
import com.universityconnect.identity.api.AccountType;
import com.universityconnect.identity.DTOs.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public AuthService(UserService userService,
                       AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest request) {
        userService.create(
                request.usn(),
                request.email(),
                request.password(),
                AccountType.STUDENT
        );
    }

    public void register(CreateUserRequest request) {

        userService.create(
                request.usn(),
                request.email(),
                request.initialPassword(),
                AccountType.STUDENT
        );
    }

    public LoginResponse login(LoginRequest request) {

       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsn(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getUsn());
        return new LoginResponse(token, "Bearer", jwtService.getExpiration());
    }
}
