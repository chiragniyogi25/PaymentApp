package com.miniproject.payment.app.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String roles="ROLE_USER";
}
