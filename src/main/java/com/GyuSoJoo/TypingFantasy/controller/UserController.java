package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.service.UserService;
import com.GyuSoJoo.TypingFantasy.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins="*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody UserVO user) {
        boolean is_success = userService.register(user);
        if (!is_success) {
            return Map.of("status", HttpStatus.CONFLICT.value(), "message", "중복된 아이디입니다.");
        }
        return Map.of("status", HttpStatus.CREATED.value(), "message", "회원가입 성공");
    }

    @GetMapping("/login")
    public Map<String, Object> login(@RequestBody UserVO user) {
        UserVO found = userService.login(user.getName(), user.getPassword());
        if (found == null) {
            return Map.of("status", HttpStatus.NOT_FOUND.value(), "message", "로그인 실패");
        }
        return Map.of("status", HttpStatus.OK.value(), "message", "로그인 성공", "name", found.getName());
    }
}
