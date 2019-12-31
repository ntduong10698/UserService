package com.tavi.duongnt.user_service.controller.user;

import com.tavi.duongnt.user_service.entities.json.JsonResult;
import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.payload.user.ForgetPasswordForm;
import com.tavi.duongnt.user_service.payload.user.LoginForm;
import com.tavi.duongnt.user_service.payload.user.RegisterForm;
import com.tavi.duongnt.user_service.security.JWTService;
import com.tavi.duongnt.user_service.security.SecurityConstants;
import com.tavi.duongnt.user_service.service.other.SendMailService;
import com.tavi.duongnt.user_service.service.user.UserService;
import com.tavi.duongnt.user_service.utils.EncodeUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/public/user")
public class UserController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/register")
    public ResponseEntity<JsonResult> register(@RequestBody RegisterForm registerForm) {
        if (registerForm.getEmail() != null && registerForm.getUsername() != null && registerForm.getPassword() != null) {
            long count  = userService.countByUsernameAndDeleted(registerForm.getUsername(), false);
            long countEmail = userService.countByEmailAndDeleted(registerForm.getEmail(), false);
            String text = "";
            if (count > 0) {
                text = "username existed";
                text += countEmail > 0 ? ", email existed" : "";
                return ResponseEntity.ok(JsonResult.build("register fail", text));
            }
            if (countEmail > 0) return  ResponseEntity.ok(JsonResult.build("register fail", "email existed"));
            UserEntity newUser = userService.register(registerForm);
            if (newUser != null) {
                return ResponseEntity.ok(JsonResult.build("register success", newUser));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JsonResult> login(@RequestBody LoginForm loginForm) {
        if (loginForm.getUsername() != null && loginForm.getPassword() != null ) {
            UserEntity user = userService.findByUsernameAndPasswordAndDeleted(loginForm.getUsername(), loginForm.getPassword(), false);
            if (user != null) {
                return ResponseEntity.ok(JsonResult.build("login success", jwtService.generateToken(user.getUsername(), SecurityConstants.EXPIRATION_TIME)));
            }
            return ResponseEntity.ok(JsonResult.build("login fail", "username or password is not correct"));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/forget-password")
    public ResponseEntity<JsonResult> forget(@RequestBody ForgetPasswordForm forgetPasswordForm){
        UserEntity userEntity = userService.findByUsername(forgetPasswordForm.getUsername());
        if (userEntity != null && userEntity.getEmail().equals(forgetPasswordForm.getEmail())){
            //send mail here
            String randomPass = RandomStringUtils.randomAlphabetic(6);
            userEntity.setPassword(EncodeUtils.getSHA256(randomPass));
            boolean result = sendMailService.sendHtmlMail(forgetPasswordForm.getEmail()
                    ,"New password for account "+forgetPasswordForm.getUsername()
                    ,"Your new password is <strong>"+randomPass+"</strong>.");
            if (result)
                return ResponseEntity.ok(JsonResult.build("Sent","Check mail please"));
            return ResponseEntity.badRequest().body(JsonResult.build("Send error", "Can not send email"));
        }
        return ResponseEntity.badRequest().body(JsonResult.build("Access denied!","Username or password is not correcr"));
    }
}
