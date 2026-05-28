package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.dto.ResponseObj;
import com.GyuSoJoo.TypingFantasy.dto.UserDTO;
import com.GyuSoJoo.TypingFantasy.service.UserRankingService;
import com.GyuSoJoo.TypingFantasy.service.UserService;
import com.GyuSoJoo.TypingFantasy.service.UserStatsService;
import com.GyuSoJoo.TypingFantasy.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins="*")
@Tag(name = "User API", description = "유저 관련 API")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserStatsService userStatsService;

    @Autowired
    private UserRankingService userRankingService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "중복된 아이디", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<Void> register(@RequestBody UserDTO.RegisterRequest request) {
        UserVO user = new UserVO();
        user.setName(request.name());
        user.setPassword(request.password());

        boolean is_success = userService.register(user);

        if (!is_success) {
            return ResponseObj.of(HttpStatus.CONFLICT.value(), "중복된 아이디입니다.");
        }
        return ResponseObj.of(HttpStatus.CREATED.value(), "회원가입 성공");
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "로그인 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<UserDTO.LoginResponse> login(@RequestBody UserDTO.LoginRequest request) {
        UserVO user = userService.login(request.name(), request.password());

        if (user == null) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "로그인 실패");
        }

        UserDTO.LoginResponse response = new UserDTO.LoginResponse(user.getId(), user.getName(), user.getMonsterIds(), user.getSelectedLang());
        return ResponseObj.of(HttpStatus.OK.value(), "로그인 성공", response);
    }

    @GetMapping("/stats/{name}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 통계 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "조회 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<UserDTO.StatsResponse> getUserStats(@PathVariable("name") String name) {
        UserVO user = userStatsService.getUserStats(name);

        if (user == null) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "유저 통계 조회 실패");
        }

        UserDTO.StatsResponse response = new UserDTO.StatsResponse(
                user.getRecordTotalErrors(),
                user.getTotalScore(),
                user.getRecordAccuracyAvg(),
                user.getRecordCpmAvg()
        );
        return ResponseObj.of(HttpStatus.OK.value(), "유저 통계 조회 성공", response);
    }

    @GetMapping("/rank/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 랭킹 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    public ResponseObj<List<UserDTO.RankingsResponse>> getUserRankingList() {
        return ResponseObj.of(HttpStatus.OK.value(), "유저 랭킹 조회 성공", userRankingService.getUserRankingList());
    }
}
