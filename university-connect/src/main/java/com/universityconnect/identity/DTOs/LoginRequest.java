package com.universityconnect.identity.DTOs;


import jakarta.validation.constraints.NotBlank;

public class LoginRequest{

    @NotBlank(message = "USN is required")
    private String usn;

    @NotBlank(message = "Password is required")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String usn, String password) {
        this.usn = usn;
        this.password = password;
    }

    public String getUsn() {
        return usn;
    }

    public String getPassword() {
        return password;
    }
}