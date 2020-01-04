package com.tavi.duongnt.user_service.controller;

import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.security.JWTService;
import com.tavi.duongnt.user_service.security.SecurityConstants;
import com.tavi.duongnt.user_service.service.user.UserService;
import com.tavi.duongnt.user_service.service_imp.social.*;
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
    private InstagramService_Impl instagramService;

    @Autowired
    private GithubService_Impl githubService;
    
    @Autowired
    private MicrosoftService_Impl microsoftService;

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
        return "redirect:"+googleService.createAuthorizationURL();
    }

    @GetMapping("/google-callback")
    public String googleCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
        googleService.createAccessToken(code);
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
        return "redirect:"+facebookService.createAuthorizationURL();
    }

    @GetMapping("/facebook-callback")
    public String facebookCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
        facebookService.createAccessToken(code);
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
    //
    @GetMapping("/login-linkedin")
    public String loginLinkedin(){
        return "redirect:"+linkedlnService.createAuthorizationURL();
    }

    @GetMapping("/linkedln-callback")
    public String linkedInCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
       linkedlnService.createAccessToken(code);
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

    //
    @GetMapping("/login-instagram")
    public String loginInsta(){
        return "redirect:"+instagramService.createAuthorizationURL();
    }

    @GetMapping("/instagram-callback")
    public String instagramCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
        instagramService.createAccessToken(code);
        UserEntity userEntity = instagramService.createUser();
        if (userEntity != null) {
            String jwtToken = jwtService.generateToken(userEntity.getUsername(), SecurityConstants.EXPIRATION_TIME);
            if (userService.findByUsername(userEntity.getUsername()) == null){
                userService.updateUser(userEntity);
            }
            return "redirect:home?token=" + jwtToken;
        }
        return "/404";
    }

    //
    @GetMapping("/login-github")
    public String loginGithub(){
        return "redirect:"+githubService.createAuthorizationURL();
    }

    @GetMapping("/github-callback")
    public String githubCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
        githubService.createAccessToken(code);
        UserEntity userEntity = githubService.createUser();
        if (userEntity != null) {
            String jwtToken = jwtService.generateToken(userEntity.getUsername(), SecurityConstants.EXPIRATION_TIME);
            if (userService.findByUsername(userEntity.getUsername()) == null){
                userService.updateUser(userEntity);
            }
            return "redirect:home?token=" + jwtToken;
        }
        return "/404";
    }

    //
    @GetMapping("/login-microsoft")
    public String loginMicrosoft(){
        return "redirect:"+microsoftService.createAuthorizationURL();
    }

    @GetMapping("/microsoft-callback")
    public String microsoftCallback(@RequestParam(name = "code") String code, HttpServletRequest request){
        microsoftService.createAccessToken(code);
        UserEntity userEntity = microsoftService.createUser();
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
