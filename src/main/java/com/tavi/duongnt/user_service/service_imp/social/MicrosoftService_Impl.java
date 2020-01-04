package com.tavi.duongnt.user_service.service_imp.social;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.service.social.SocialService;
import com.tavi.duongnt.user_service.utils.APIBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MicrosoftService_Impl implements SocialService {

    @Value("${spring.social.microsoft.appId}")
    private String clientId;
    @Value("${spring.social.microsoft.appSecret}")
    private String secret;
    @Value("${microsoft.link.redirect}")
    private String redirect;
    @Value("${microsoft.link.get.user_info}")
    private String linkUser;
    @Value("${microsoft.scope}")
    private String scope;
    @Value("${microsoft.link.oauth}")
    private String oauthApi;
    @Value("${microsoft.link.get.token}")
    private String tokenApi;

    private String accessToken;

    private static final Logger LOGGER = Logger.getLogger(MicrosoftService_Impl.class.getName());


    @Override
    public String createAuthorizationURL() {
        return new APIBuilder(oauthApi)
                .addParam("client_id", clientId)
                .addParam("redirect_uri", redirect)
                .addParam("response_type", "code")
                .addParam("scope", scope)
                .addParam("state","123")
                .build();
    }

    @Override
    public void createAccessToken(String code) {
        try {
            String link = new APIBuilder(tokenApi)
                    .addParam("scope",scope)
                    .addParam("redirect_uri",redirect)
                    .build();

            String body = "grant_type=authorization_code&client_secret="+secret+"&code="+code+"&client_id="+clientId;

            String result = Request.Post(tokenApi)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .body(new StringEntity(body,ContentType.APPLICATION_FORM_URLENCODED))
                    .execute()
                    .returnContent()
                    .asString();
            JSONObject jsonObject = new JSONObject(result);
            accessToken = jsonObject.getString("access_token");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-token-error: {0}", ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public UserEntity createUser() {
        try{
            String link = new APIBuilder(linkUser).build();
            String result = Request
                    .Get(link)
                    .addHeader("Authorization","Bearer "+accessToken)
                    .execute()
                    .returnContent()
                    .asString();
            System.out.println(result);
            JSONObject json = new JSONObject(result);
            String phone = null;
            if (json.get("mobilePhone") != null)
                phone = json.get("mobilePhone").toString();
            return UserEntity.builder()
                    .username("microsoft "+json.getString("id"))
                    .phoneNumber(phone)
                    .firstName(convertUTF8(json.getString("givenName")))
                    .lastName(convertUTF8(json.getString("surname")))
                    .email(json.getString("userPrincipalName"))
                    .deleted(false)
                    .social(6)
                    .status(1)
                    .initDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                    .build();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private String convertUTF8(String src){
        return new String(src.getBytes(),StandardCharsets.UTF_8);
    }
}
