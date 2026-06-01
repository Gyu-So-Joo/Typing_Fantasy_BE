package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.dto.RecordDTO;
import com.GyuSoJoo.TypingFantasy.dto.ResponseObj;
import com.GyuSoJoo.TypingFantasy.dto.UserDTO;
import com.GyuSoJoo.TypingFantasy.service.RecordService;
import com.GyuSoJoo.TypingFantasy.service.UserRankingService;
import com.GyuSoJoo.TypingFantasy.service.UserService;
import com.GyuSoJoo.TypingFantasy.vo.RecordVO;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins="*")
@Tag(name = "User API", description = "유저 관련 API")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRankingService userRankingService;

    @Autowired
    private RecordService recordService;


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

        boolean isSuccess = userService.register(user);

        if (!isSuccess) {
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

        UserDTO.LoginResponse response = new UserDTO.LoginResponse(user.getId(), user.getName(), user.getSelectedLang());
        return ResponseObj.of(HttpStatus.OK.value(), "로그인 성공", response);
    }

    @GetMapping("/{id}/record/recent")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 통계 조회 (최근 10개)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "유저 통계 조회 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "유저 통계 조회 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<RecordDTO.userRecordResponse> getUserRecordLimit10(@PathVariable("id") long id) {
        List<RecordVO> records = recordService.getRecordsByUserIdLimit10(id);

        if (records.isEmpty()) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "유저 통계 조회 실패");
        }

        int recordCnt = records.size();

        BigDecimal totalAccuracy = BigDecimal.ZERO;
        int totalCpm = 0;
        int totalSpecialCharError = 0;
        int totalCaseMismatchError = 0;
        int totalNormalTextError = 0;
        long totalScore = userService.findTotalScoreById(id);

        for (RecordVO record : records) {
            totalAccuracy = totalAccuracy.add(record.getAccuracy());
            totalCpm += record.getCpm();
            totalSpecialCharError += record.getSpecialCharError();
            totalCaseMismatchError += record.getCaseMismatchError();
            totalNormalTextError += record.getNormalTextError();
        }

        RecordDTO.userRecordResponse response = new RecordDTO.userRecordResponse(
                totalAccuracy.divide(BigDecimal.valueOf(recordCnt), 2, RoundingMode.HALF_UP),
                totalCpm / recordCnt,
                totalSpecialCharError,
                totalCaseMismatchError,
                totalNormalTextError,
                totalScore
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

    @PatchMapping("/lang")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 언어 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "변경 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "변경 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<Void> setSelectedLang(@RequestBody UserDTO.LangRequest request) {
        boolean isSuccess = userService.setSelectedLang(request.id(), request.selectedLang());

        if (!isSuccess) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "유저 언어 변경 실패");
        }
        return ResponseObj.of(HttpStatus.OK.value(), "유저 언어 변경 성공");
    }

    @GetMapping("/{id}/monster-ids")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 몬스터 아이디 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json"))
    public ResponseObj<List<Integer>> getMonsterIds(@PathVariable("id") long id) {
        return ResponseObj.of(HttpStatus.OK.value(), "유저 몬스터 아이디 조회 성공", userService.findMonsterIdsById(id));
    }
}
