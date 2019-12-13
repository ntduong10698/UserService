package com.tavi.duongnt.user_service.controller.user.secret;

import com.tavi.duongnt.user_service.entities.json.JsonResult;
import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.repository.user.UserRepository;
import com.tavi.duongnt.user_service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1/admin/user")
public class UserControllerAdmin {

    @Autowired
    UserService userService;

    @PutMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        int count = userService.delete(id);
        if (count > 0) {
            return ResponseEntity.ok(JsonResult.build("delete success", count));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        UserEntity userEntity = userService.findById(id, true);
        if (userEntity != null) {
            return ResponseEntity.ok(JsonResult.build("success", userEntity));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
