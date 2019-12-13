package com.tavi.duongnt.user_service.controller.society.secret;

import com.tavi.duongnt.user_service.entities.json.JsonResult;
import com.tavi.duongnt.user_service.entities.society.SocietyEntity;
import com.tavi.duongnt.user_service.service.society.SocietyService;
import com.tavi.duongnt.user_service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/admin/society")
public class SocietyControllerAdmin {

    @Autowired
    private SocietyService societyService;

    @PutMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam(value = "id") int id) {
        int count = societyService.delete(id);
        if (count > 0) {
            return ResponseEntity.ok(JsonResult.build("delete success", count));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody SocietyEntity societyEntity) {
        long count = societyService.countSocietyByNameAndStatus(societyEntity.getName(), true);
        if (count > 0) {
            return ResponseEntity.ok(JsonResult.build("upload society fail", "society existed"));
        }
        societyEntity.setStatus(true);
        SocietyEntity newSocietyEntity = societyService.upload(societyEntity);
        if (newSocietyEntity != null) {
            return ResponseEntity.ok(JsonResult.build("upload society success", newSocietyEntity));
        }
        return ResponseEntity.badRequest().build();
    }
}
