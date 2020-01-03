package com.tavi.duongnt.user_service.service.social;

import com.tavi.duongnt.user_service.entities.user.UserEntity;

public interface LinkedInService {

    String createLinkedlnAuthorizationURL();

    void createLinkedlnAccessToken(String code);

    UserEntity createUser();
    
}
