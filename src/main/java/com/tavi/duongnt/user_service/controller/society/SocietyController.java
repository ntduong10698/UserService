package com.tavi.duongnt.user_service.controller.society;

import com.tavi.duongnt.user_service.entities.json.JsonResult;
import com.tavi.duongnt.user_service.entities.society.SocietyEntity;
import com.tavi.duongnt.user_service.service.society.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/v1/public/society")
public class SocietyController {

    @Autowired
    private SocietyService societyService;

    @GetMapping("/find-by-name")
    public ResponseEntity<JsonResult> findByName(@RequestParam("name") String name) {
        List<SocietyEntity> list = societyService.findByNameAndStatus(name, true);
        if (list != null) {
            return ResponseEntity.ok(JsonResult.build("success", list));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public ResponseEntity<JsonResult> findAll() {
        List<SocietyEntity> list = societyService.findAll();
        if (list != null) {
            return ResponseEntity.ok(JsonResult.build("success", list));
        }
        return ResponseEntity.badRequest().build();
    }
}
