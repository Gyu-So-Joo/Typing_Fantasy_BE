package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.dto.MonsterDTO;
import com.GyuSoJoo.TypingFantasy.dto.ResponseObj;
import com.GyuSoJoo.TypingFantasy.service.MonsterService;
import com.GyuSoJoo.TypingFantasy.vo.MonsterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/monster")
@CrossOrigin(origins = "*")
@Tag(name = "Monster API", description = "몬스터 관련 API")
public class MonsterController {
    @Autowired
    MonsterService monsterService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "몬스터 데이터 로드 (전체)")
    @ApiResponse(responseCode = "200", description = "데이터 로드 성공", content = @Content(mediaType = "application/json"))
    public ResponseObj<List<MonsterVO>> getAllMonsters() {
        return ResponseObj.of(HttpStatus.OK.value(), "몬스터 데이터 로드 성공", monsterService.getAllMonsters());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "몬스터 데이터 로드 (단일)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "몬스터 데이터 로드 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "몬스터 데이터 로드 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<MonsterVO> getMonster(@PathVariable("id") long id) {
        MonsterVO monster = monsterService.getMonster(id);
        if (monster == null) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "몬스터 데이터 로드 실패");
        }
        return ResponseObj.of(HttpStatus.OK.value(), "몬스터 데이터 로드 성공", monster);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "몬스터 데이터 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "201", description = "몬스터 데이터 추가 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<MonsterVO> addMonster(@RequestBody MonsterDTO.AddMonsterRequest request) {
        MonsterVO monster = new MonsterVO();
        monster.setName(request.name());
        monster.setLevel(request.level());
        monster.setDetail(request.detail());
        monster.setJsCode(request.jsCode());
        monster.setJavaCode(request.javaCode());

        boolean is_success = monsterService.addMonster(monster);

        if (!is_success) {
            return ResponseObj.of(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
        }
        return ResponseObj.of(HttpStatus.CREATED.value(), "몬스터 데이터 추가 성공", monsterService.getMonster(monster.getId()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "몬스터 데이터 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "몬스터 데이터 삭제 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "몬스터 데이터 삭제 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<Void> deleteMonster(@PathVariable("id") long id) {
        boolean isSuccess = monsterService.deleteMonster(id);

        if (!isSuccess) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "몬스터 데이터 삭제 실패");
        }
        return ResponseObj.of(HttpStatus.NO_CONTENT.value(), "몬스터 데이터 삭제 성공");
    }
}
