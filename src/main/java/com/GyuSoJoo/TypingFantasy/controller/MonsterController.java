package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.dto.MonsterDTO;
import com.GyuSoJoo.TypingFantasy.dto.ResponseObj;
import com.GyuSoJoo.TypingFantasy.service.FileService;
import com.GyuSoJoo.TypingFantasy.service.MonsterService;
import com.GyuSoJoo.TypingFantasy.vo.MonsterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/monster")
@CrossOrigin(origins = "*")
@Tag(name = "Monster API", description = "몬스터 관련 API")
public class MonsterController {
    @Autowired
    MonsterService monsterService;

    @Autowired
    FileService fileService;

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
        MonsterVO monster = monsterService.getMonsterById(id);
        if (monster == null) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "몬스터 데이터 로드 실패");
        }
        return ResponseObj.of(HttpStatus.OK.value(), "몬스터 데이터 로드 성공", monster);
    }


    // MultipartFile 타입 필드에 대한 커스텀 변환 규칙 등록
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(org.springframework.web.multipart.MultipartFile.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null);
                }
            }
        });
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "몬스터 데이터 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "이미 존재하는 몬스터 데이터", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "201", description = "몬스터 데이터 추가 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<MonsterVO> addMonster(@ModelAttribute MonsterDTO.AddMonsterRequest request) throws IOException {
        MonsterVO existing = monsterService.getMonsterByName(request.getName());
        if (existing != null) {
            return ResponseObj.of(HttpStatus.CONFLICT.value(), "이미 존재하는 몬스터 데이터입니다.", existing);
        }

        MonsterVO monster = new MonsterVO();
        monster.setName(request.getName());
        monster.setLevel(request.getLevel());
        monster.setDetail(request.getDetail());
        monster.setJsCode(request.getJsCode());
        monster.setJavaCode(request.getJavaCode());

        monster.setSilhouetteImg(request.getSilhouetteImg() == null ? "" : fileService.uploadImage(request.getSilhouetteImg()));
        monster.setNormalImg(request.getNormalImg() == null ? "" : fileService.uploadImage(request.getNormalImg()));

        boolean is_success = monsterService.addMonster(monster);

        if (!is_success) {
            return ResponseObj.of(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
        }
        return ResponseObj.of(HttpStatus.CREATED.value(), "몬스터 데이터 추가 성공", monsterService.getMonsterByName(monster.getName()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "몬스터 데이터 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "몬스터 데이터 삭제 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "몬스터 데이터 삭제 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<Void> deleteMonster(@PathVariable("id") long id) {
        boolean isSuccess = monsterService.deleteMonster(id);

        if (!isSuccess) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "몬스터 데이터 삭제 실패");
        }
        return ResponseObj.of(HttpStatus.OK.value(), "몬스터 데이터 삭제 성공");
    }
}
