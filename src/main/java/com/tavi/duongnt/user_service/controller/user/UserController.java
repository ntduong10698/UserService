package com.tavi.duongnt.user_service.controller.user;

import com.tavi.duongnt.user_service.entities.json.JsonResult;
import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.payload.user.LoginForm;
import com.tavi.duongnt.user_service.payload.user.RegisterForm;
import com.tavi.duongnt.user_service.security.JWTService;
import com.tavi.duongnt.user_service.security.SecurityConstants;
import com.tavi.duongnt.user_service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/public/user")
public class UserController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    UserService userService;

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
}
