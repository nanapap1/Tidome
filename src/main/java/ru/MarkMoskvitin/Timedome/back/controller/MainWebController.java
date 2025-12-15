package ru.MarkMoskvitin.Timedome.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;
@Controller
public class MainWebController {

    @GetMapping("/")
    public ResponseEntity<?> check()
    {
        Map<String,Boolean> res = new HashMap<>();
        res.put("go",true);
        return ResponseEntity.ok(res);
    }

}
