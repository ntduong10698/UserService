package com.tavi.duongnt.user_service.service.social;

import com.tavi.duongnt.user_service.entities.user.UserEntity;

public interface SocialService {

    String createAuthorizationURL();

    void createAccessToken(String code);

    UserEntity createUser();

}
