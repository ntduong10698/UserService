package com.tavi.duongnt.user_service.service_imp.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tavi.duongnt.user_service.entities.user.GooglePojo;
import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.repository.user.UserRepository;
import com.tavi.duongnt.user_service.service.social.GoogleService;
import lombok.Getter;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Getter
public class GoogleService_Impl implements GoogleService {

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.social.google.appId}")
    private String appId;
    @Value("${spring.social.google.appSecret}")
    private String appSecret;
    @Value("${google.link.redirect}")
    private String redirect;
    @Value("${google.link.get.user_info}")
    private String linkUser;
    @Value("${google.scope}")
    private String scope;

    private String accessToken;
    private String userID;

    private static final Logger LOGGER = Logger.getLogger(GoogleService_Impl.class.getName());

    @Override
    public String createGoogleAuthorizationURL() {
        try {
            GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(appId, appSecret);
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
    public void createGoogleAccessToken(String code) {
        try {
            GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(appId, appSecret);
            AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, redirect, null);
            accessToken = accessGrant.getAccessToken();
            LOGGER.log(Level.INFO, "create-token-success: " + accessToken);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-token-error: {0}", ex.getMessage());
        }
    }

    @Override
    public UserEntity createUser() {
        String link = linkUser + accessToken;
        try {
            String response = Request.Get(link).execute().returnContent().asString();
            System.out.println(GoogleService_Impl.class.getName() + " : " + response);
            ObjectMapper mapper = new ObjectMapper();
            GooglePojo pojo = mapper.readValue(response, GooglePojo.class);
            userID = pojo.getId();
            return UserEntity.builder()
                    .username(pojo.getId())
                    .email(pojo.getEmail())
                    .lastName(pojo.getFamily_name())
                    .firstName(pojo.getGiven_name())
                    .initDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                    .social(1)
                    .status(1)
                    .deleted(false)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
