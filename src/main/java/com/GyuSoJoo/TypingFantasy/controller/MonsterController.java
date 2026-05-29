package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.dto.MonsterDTO;
import com.GyuSoJoo.TypingFantasy.dto.ResponseObj;
import com.GyuSoJoo.TypingFantasy.service.FileService;
import com.GyuSoJoo.TypingFantasy.service.MonsterService;
import com.GyuSoJoo.TypingFantasy.service.UserService;
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

    @Autowired
    UserService userService;

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

        monster.setNormalImg(request.getNormalImg() == null ? "" : fileService.uploadImage(request.getNormalImg()));

        boolean isSuccess = monsterService.addMonster(monster);

        if (!isSuccess) {
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

    @PutMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "몬스터 데이터 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "존재하지 않는 몬스터 데이터", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "몬스터 데이터 수정 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<MonsterVO> updateMonster(@ModelAttribute MonsterDTO.UpdateMonsterRequest request) throws IOException {
        MonsterVO existing = monsterService.getMonsterById(request.getId());
        if (existing == null) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "존재하지 않는 몬스터 데이터입니다.");
        }

        MonsterVO monster = new MonsterVO();
        monster.setId(request.getId());
        monster.setName(request.getName());
        monster.setLevel(request.getLevel());
        monster.setDetail(request.getDetail());
        monster.setJsCode(request.getJsCode());
        monster.setJavaCode(request.getJavaCode());

        monster.setNormalImg(request.getNormalImg() == null ?
                existing.getNormalImg() : fileService.uploadImage(request.getNormalImg()));

        boolean isSuccess = monsterService.UpdateMonster(monster);

        if (!isSuccess) {
            return ResponseObj.of(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
        }

        return ResponseObj.of(HttpStatus.OK.value(), "몬스터 데이터 수정 완료", monster);
    }

    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "랜덤 몬스터 데이터 로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "몬스터 데이터 로드 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "몬스터 데이터 로드 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<MonsterVO> getMonsterByLevelAndUserId(@RequestParam("userId") Long userId, @RequestParam("level") int level) {
        // 입력받은 level의 몬스터들 조회
        List<MonsterVO> monsters = monsterService.getMonstersByLevel(level);
        if (monsters.isEmpty()) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "몬스터 데이터 로드 실패");
        }

        // 유저가 가지고 있는 monsterIds를 JSON -> List로 조회
        List<Integer> monsterIdList = userService.findMonsterIdsById(userId);

        // monsterIds의 값과 monsterId를 비교해서 다른 것만 List로 필터링
        List<MonsterVO> filteredMonsters = monsters.stream()
                .filter(monster -> !monsterIdList.contains(monster.getId().intValue()))
                .toList();

        // 필터링한 List가 비어있으면, monsters 사용 (몬스터를 모두 가진 경우, 전체에서 선택)
        List<MonsterVO> targetList = filteredMonsters.isEmpty() ? monsters : filteredMonsters;

        // 랜덤 선택한 monster 리턴
        int selectedNum = (int) (Math.random() * targetList.size());
        return ResponseObj.of(HttpStatus.OK.value(), "몬스터 데이터 로드 완료", targetList.get(selectedNum));
    }
}
