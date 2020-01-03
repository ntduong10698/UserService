package com.tavi.duongnt.user_service.service.social;

import com.tavi.duongnt.user_service.entities.user.UserEntity;

public interface FacebookService {

    String createFacebookAuthorizationURL();

    void createFacebookAccessToken(String code);

    UserEntity createUser();

}
