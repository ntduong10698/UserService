package com.tavi.duongnt.user_service.service_imp.social;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.service.social.FacebookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacebookService_Impl implements FacebookService {

    @Value("${spring.social.facebook.appId}")
    private String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    private String facebookAppSecret;
    @Value("${facebook.link.redirect}")
    private String redirect;
    @Value("${facebook.link.get.user_info}")
    private String linkUser;
    @Value("${facebook.scope}")
    private String scope;

    private String accessToken;
    private String userID;

    private static final Logger LOGGER = Logger.getLogger(FacebookService_Impl.class.getName());

    @Override
    public String createFacebookAuthorizationURL() {
        try {
            FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookAppSecret);
            OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
            OAuth2Parameters params = new OAuth2Parameters();
            params.setRedirectUri(redirect);
            params.setScope(scope);
            LOGGER.log(Level.INFO, "create-url-success");
            return oauthOperations.buildAuthorizeUrl(params);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-url-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public void createFacebookAccessToken(String code) {
        try {
            FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookAppSecret);
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
            FacebookClient client = new DefaultFacebookClient(Version.VERSION_3_0).createClientWithAccessToken(accessToken);
            JsonObject user = client.fetchObject("me", JsonObject.class, Parameter.with("fields", "id,email,first_name,last_name,birthday,hometown"));
            System.out.println("User=" + user);
            String[] date = user.getString("birthday", "").split("/");
            return UserEntity.builder()
                    .username(user.get("id").asString())
                    .email(user.getString("email", ""))
                    .firstName(convertUTF8(user.getString("first_name", "")))
                    .lastName(convertUTF8(user.getString("last_name", "")))
                    .address(convertUTF8(user.getString("hometown", "")))
                    .birthday(date[2] + "/" + date[1] + "/" + date[0])
                    .status(1)
                    .deleted(false)
                    .initDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                    .social(2)
                    .build();
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"create user error: {}", ex.getMessage());
        }
        return null;
    }

    private String convertUTF8(String src){
        return new String(src.getBytes(),StandardCharsets.UTF_8);
    }
}
