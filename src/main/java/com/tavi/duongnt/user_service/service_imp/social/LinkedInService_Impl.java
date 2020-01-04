package com.tavi.duongnt.user_service.service_imp.social;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.service.social.SocialService;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LinkedInService_Impl implements SocialService {


    @Value("${spring.social.linkedln.appId}")
    private String consumerId;
    @Value("${spring.social.linkedln.appSecret}")
    private String secretKey;
    @Value("${linkedln.link.redirect}")
    private String redirect;
    @Value("${linkedln.link.get.user_info}")
    private String linkUser;
    @Value("${linkedln.scope}")
    private String scope;

    private String accessToken;
    private String userID;

    private static final Logger LOGGER = Logger.getLogger(LinkedInService_Impl.class.getName());

    @Override
    public String createAuthorizationURL() {
        try {
            LinkedInConnectionFactory connectionFactory = new LinkedInConnectionFactory(consumerId, secretKey);
            OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
            OAuth2Parameters params = new OAuth2Parameters();
            params.setRedirectUri(redirect);
            params.setScope(scope);
            return oauthOperations.buildAuthorizeUrl(params);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-url-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public void createAccessToken(String code) {
        try {
            LinkedInConnectionFactory connectionFactory = new LinkedInConnectionFactory(consumerId, secretKey);
            AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, redirect, null);
            accessToken = accessGrant.getAccessToken();
            LOGGER.log(Level.INFO, "create-token-success: " + accessToken);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-token-error: {0}", ex.getMessage());
        }
    }

    @Override
    public UserEntity createUser() {
        try {
            String link = linkUser;
            String response = Request.Get(link)
                    .addHeader("Content-Type","application/json")
                    .addHeader("Authorization","Bearer "+accessToken)
                    .execute().returnContent().asString();
            JSONObject object = new JSONObject(response);
            System.out.println(LinkedInService_Impl.class.getName() + " : " + response);
            return UserEntity.builder()
                    .username("linkedin"+object.getString("id"))
                    .firstName(object.getString("localizedFirstName"))
                    .lastName(object.getString("localizedLastName"))
                    .social(4)
                    .status(1)
                    .deleted(false)
                    .initDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                    .build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-user-error: {0}" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
