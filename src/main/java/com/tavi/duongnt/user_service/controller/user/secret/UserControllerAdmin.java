package com.tavi.duongnt.user_service.controller.user.secret;

import com.tavi.duongnt.user_service.entities.json.JsonResult;
import com.tavi.duongnt.user_service.entities.json.PageResult;
import com.tavi.duongnt.user_service.entities.user.UserEntity;
import com.tavi.duongnt.user_service.service.user.UserService;
import com.tavi.duongnt.user_service.utils.EncodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
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

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody UserEntity userEntity,
                                             @RequestParam(value = "change-password", defaultValue = "false", required = false) boolean changePass){
        if (changePass){
            userEntity.setPassword(EncodeUtils.getSHA256(userEntity.getPassword()));
        }
        UserEntity newUser = userService.updateUser(userEntity);
        if (newUser != null){
            return ResponseEntity.ok(JsonResult.build("update success",newUser));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<JsonResult> profile(HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        UserEntity userEntity = userService.findByUsername(username);
        if (userEntity != null)
            return ResponseEntity.ok(JsonResult.build("success",userEntity));
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        UserEntity userEntity = userService.findById(id, true);
        if (userEntity != null) {
            return ResponseEntity.ok(JsonResult.build("success", userEntity));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/find-all")
    public ResponseEntity<PageResult> findAll(@RequestParam(value = "deleed", defaultValue = "false", required = false) Boolean deleted
                                                ,@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(value = "size", defaultValue = "10", required = false) int size){
        Page<UserEntity> listUser = userService.findAll(deleted,page, size);
        if (listUser != null) {
            return ResponseEntity.ok(PageResult.build("success", listUser.toList(), listUser.getTotalElements(), listUser.getTotalPages()));
        }
        return ResponseEntity.badRequest().build();
    }
}
