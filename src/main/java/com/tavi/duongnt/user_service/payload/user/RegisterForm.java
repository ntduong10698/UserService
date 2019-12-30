package com.tavi.duongnt.user_service.payload.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RegisterForm {

    private String username;

    private String password;

    private String email;
}
