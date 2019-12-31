package com.tavi.duongnt.user_service.payload.user;

import lombok.Data;

@Data
public class ForgetPasswordForm {

    private String username;

    private String email;

}
