package ru.MarkMoskvitin.Timedome.back.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.MarkMoskvitin.Timedome.back.service.UserServiceImp;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserServiceImp userService;
    @GetMapping("/user/{login}")
    public ResponseEntity<?> check(@PathVariable String login)
    {
        boolean free = userService.isFree(login);
        Map<String,Boolean> res = new HashMap<>();
        res.put("free",free);
        return ResponseEntity.ok(res);
    }
}
