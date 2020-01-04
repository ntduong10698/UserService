package com.tavi.duongnt.user_service.service_imp.social;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.service.social.SocialService;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GithubService_Impl implements SocialService {

    @Value("${spring.social.github.appId}")
    private String clientId;
    @Value("${spring.social.github.appSecret}")
    private String secret;
    @Value("${github.link.redirect}")
    private String redirect;
    @Value("${github.link.get.user_info}")
    private String linkUser;
    @Value("${github.scope}")
    private String scope;
    @Value("${github.link.oauth}")
    private String oauthApi;
    @Value("${github.link.get.token}")
    private String tokenApi;

    private String accessToken;

    private static final Logger LOGGER = Logger.getLogger(GithubService_Impl.class.getName());

    @Override
    public String createAuthorizationURL() {
        return oauthApi+"?client_id=" + clientId + "&redirect_uri=" + redirect + "&scope=" + scope;
    }

    @Override
    public void createAccessToken(String code) {
        try {
            Request request = Request
                    .Get(tokenApi+"?client_id=" + clientId + "&client_secret=" + secret + "&code=" + code)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json");
            JSONObject json = new JSONObject(request.execute().returnContent().asString());
            accessToken = json.getString("access_token");
            LOGGER.log(Level.INFO, "create-token-success: " + accessToken);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-token-error: {0}", ex.getMessage());
        }
    }

    @Override
    public UserEntity createUser() {
        try {
            Request request = Request
                    .Get(linkUser)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "token " + accessToken);
            JSONObject json = new JSONObject(request.execute().returnContent().asString());
            System.out.println(json.toString());
            String email = null;
            try {
                email = json.getString("email");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "get-email-error: {0}", ex.getMessage());
                //ex.printStackTrace();
            }
            return UserEntity.builder()
                    .username("github" + json.getInt("id"))
                    .email(email)
                    .firstName(json.getString("name"))
                    .deleted(false)
                    .status(1)
                    .initDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                    .social(5)
                    .build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-user-error: {0}", ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
