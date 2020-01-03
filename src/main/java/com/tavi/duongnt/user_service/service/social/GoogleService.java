package com.tavi.duongnt.user_service.service.social;

import com.tavi.duongnt.user_service.entities.user.UserEntity;

public interface GoogleService {

    String createGoogleAuthorizationURL();

    void createGoogleAccessToken(String code);

    UserEntity createUser();

}
