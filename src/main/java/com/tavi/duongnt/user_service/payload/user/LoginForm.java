package com.tavi.duongnt.user_service.payload.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginForm {

    private String username;

    private String password;
}
