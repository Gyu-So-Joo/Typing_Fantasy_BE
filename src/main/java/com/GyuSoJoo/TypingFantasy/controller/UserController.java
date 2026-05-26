package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.service.UserService;
import com.GyuSoJoo.TypingFantasy.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/user")
@Tag(name = "User API", description = "유저 관련 API")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "중복된 아이디", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(mediaType = "application/json"))
    })
    public Map<String, Object> register(@RequestBody UserVO user) {
        boolean is_success = userService.register(user);
        if (!is_success) {
            return Map.of("status", HttpStatus.CONFLICT.value(), "message", "중복된 아이디입니다.");
        }
        return Map.of("status", HttpStatus.CREATED.value(), "message", "회원가입 성공");
    }

    @GetMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "로그인 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json"))
    })
    public Map<String, Object> login(@RequestBody UserVO user) {
        UserVO found = userService.login(user.getName(), user.getPassword());
        if (found == null) {
            return Map.of("status", HttpStatus.NOT_FOUND.value(), "message", "로그인 실패");
        }
        return Map.of("status", HttpStatus.OK.value(), "message", "로그인 성공", "name", found.getName());
    }
}
