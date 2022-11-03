package com.miniproject.payment.app.entity;

/**Input for JWT Token*/
public class AuthenticationRequestEntity {

    private String email;
    private String password;

    public AuthenticationRequestEntity() {
    }

    public AuthenticationRequestEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
