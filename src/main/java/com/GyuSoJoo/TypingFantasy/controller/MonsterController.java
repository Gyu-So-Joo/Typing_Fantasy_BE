package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.dto.ResponseObj;
import com.GyuSoJoo.TypingFantasy.service.MonsterService;
import com.GyuSoJoo.TypingFantasy.vo.MonsterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/monster")
@CrossOrigin(origins="*")
@Tag(name = "Monster API", description = "몬스터 관련 API")
public class MonsterController {
    @Autowired
    MonsterService monsterService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "몬스터 데이터 로드")
    @ApiResponse(responseCode = "200", description = "데이터 로드 성공", content = @Content(mediaType = "application/json"))
    public ResponseObj<List<MonsterVO>> getAllMonsters() {
        return ResponseObj.of(HttpStatus.OK.value(), "몬스터 데이터 로드 성공", monsterService.getAllMonsters());
    }

}
