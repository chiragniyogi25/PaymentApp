package com.miniproject.payment.app.entity;

import java.util.Collection;

public class AuthenticationResponse {
    private final String jwt;
    private final String email;
    private final Collection<?> roles;

    public String getEmail() {
        return email;
    }

    public Collection<?> getRoles() {
        return roles;
    }

    public AuthenticationResponse(String jwt, String email, Collection<?> roles){
        this.jwt = jwt;
        this.email= email;
        this.roles = roles;
    }

    public String getJwt(){
        return jwt;
    }
}
