package com.tavi.duongnt.user_service.service_imp.social;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.service.social.SocialService;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InstagramService_Impl implements SocialService {

    @Value("${spring.social.instagram.appId}")
    private String clientId;
    @Value("${spring.social.instagram.appSecret}")
    private String secret;
    @Value("${instagram.link.redirect}")
    private String redirect;
    @Value("${instagram.link.get.user_info}")
    private String linkUser;
    @Value("${instagram.scope}")
    private String scope;

    private String accessToken;

    private static final Logger LOGGER = Logger.getLogger(InstagramService_Impl.class.getName());

    @Override
    public String createAuthorizationURL() {
        try {
            InstagramAuthService authService = new InstagramAuthService();
            InstagramService service = authService.apiKey(clientId).apiSecret(secret).callback(redirect).scope(scope).build();
            return service.getAuthorizationUrl();
            // return "https://api.instagram.com/oauth/authorize/?client_id="+clientId+"&redirect_uri="+redirect+"&response_type=code";
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-url-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public void createAccessToken(String code) {
        try {
            InstagramAuthService authService = new InstagramAuthService();
            InstagramService service = authService.apiKey(clientId).apiSecret(secret).callback(redirect).scope(scope).build();
            accessToken = service.getAccessToken(new Verifier(code)).getToken();
            LOGGER.log(Level.INFO, "create-token-success: " + accessToken);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "create-token-error: {0}", ex.getMessage());
        }
    }

    @Override
    public UserEntity createUser() {
        return null;
    }
}
