package com.tavi.duongnt.user_service.controller;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.security.JWTService;
import com.tavi.duongnt.user_service.security.SecurityConstants;
import com.tavi.duongnt.user_service.service.user.UserService;
import com.tavi.duongnt.user_service.service_imp.social.FacebookService_Impl;
import com.tavi.duongnt.user_service.service_imp.social.GoogleService_Impl;
import com.tavi.duongnt.user_service.service_imp.social.LinkedInService_Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ViewController {

    @Autowired
    private GoogleService_Impl googleService;

    @Autowired
    private FacebookService_Impl facebookService;

    @Autowired
    private LinkedInService_Impl linkedlnService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/home"})
    public String home() {
        return "home";
    }

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/login-google")
    public String loginGoogle(){
        return "redirect:"+googleService.createGoogleAuthorizationURL();
    }

    @GetMapping("/google-callback")
    public String googleCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
        googleService.createGoogleAccessToken(code);
        UserEntity userEntity = googleService.createUser();
        if (userEntity != null) {
            String jwtToken = jwtService.generateToken(userEntity.getUsername(), SecurityConstants.EXPIRATION_TIME);
            if (userService.findByUsername(userEntity.getUsername()) == null){
                userService.updateUser(userEntity);
            }
            return "redirect:home?token=" + jwtToken;
        }
        return "/404";
    }

    @GetMapping("/login-facebook")
    public String loginFacebook(){
        return "redirect:"+facebookService.createFacebookAuthorizationURL();
    }

    @GetMapping("/facebook-callback")
    public String facebookCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
        facebookService.createFacebookAccessToken(code);
        UserEntity userEntity = facebookService.createUser();
        if (userEntity != null) {
            String jwtToken = jwtService.generateToken(userEntity.getUsername(), SecurityConstants.EXPIRATION_TIME);
            if (userService.findByUsername(userEntity.getUsername()) == null){
                userService.updateUser(userEntity);
            }
            return "redirect:home?token=" + jwtToken;
        }
        return "/404";
    }

    @GetMapping("/login-linkedin")
    public String loginLinkedin(){
        return "redirect:"+linkedlnService.createLinkedlnAuthorizationURL();
    }

    @GetMapping("/linkedln-callback")
    public String linkedInCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
       linkedlnService.createLinkedlnAccessToken(code);
        UserEntity userEntity = linkedlnService.createUser();
        if (userEntity != null) {
            String jwtToken = jwtService.generateToken(userEntity.getUsername(), SecurityConstants.EXPIRATION_TIME);
            if (userService.findByUsername(userEntity.getUsername()) == null){
                userService.updateUser(userEntity);
            }
            return "redirect:home?token=" + jwtToken;
        }
        return "/404";
    }

}
