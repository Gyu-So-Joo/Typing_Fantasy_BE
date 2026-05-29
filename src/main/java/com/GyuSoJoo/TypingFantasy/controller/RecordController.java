package com.GyuSoJoo.TypingFantasy.controller;

import com.GyuSoJoo.TypingFantasy.dto.RecordDTO;
import com.GyuSoJoo.TypingFantasy.dto.ResponseObj;
import com.GyuSoJoo.TypingFantasy.service.RecordService;
import com.GyuSoJoo.TypingFantasy.service.UserService;
import com.GyuSoJoo.TypingFantasy.vo.RecordVO;
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
@RequestMapping("api/record")
@CrossOrigin(origins = "*")
@Tag(name = "Record API", description = "레코드 관련 API")
public class RecordController {
    @Autowired
    RecordService recordService;

    @Autowired
    UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "레코드 데이터 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "201", description = "레코드 데이터 추가 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<Void> addRecord(@RequestBody RecordDTO.InsertRequest request) {
        RecordVO record = new RecordVO();
        record.setUserId(request.userId());
        record.setUserName(request.userName());
        record.setSelectedLang(request.selectedLang());
        record.setTimer(request.timer());
        record.setAccuracy(request.accuracy());
        record.setCpm(request.cpm());
        record.setSpecialCharError(request.specialCharError());
        record.setCaseMismatchError(request.caseMismatchError());
        record.setIndentationError(request.indentationError());
        record.setNormalTextError(request.normalTextError());

        boolean isSuccess1 = recordService.addRecord(record);

        if (!isSuccess1) {
            return ResponseObj.of(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
        }

        boolean isSuccess2 = userService.setSelectedLang(record.getUserId(), request.selectedLang());

        if (!isSuccess2) {
            return ResponseObj.of(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
        }

        return ResponseObj.of(HttpStatus.CREATED.value(), "레코드 데이터 추가 성공");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "레코드 데이터 조회 (전체)")
    @ApiResponse(responseCode = "200", description = "레코드 데이터 조회 성공 (전체)", content = @Content(mediaType = "application/json"))
    public ResponseObj<List<RecordVO>> getAllRecords() {
        return ResponseObj.of(HttpStatus.OK.value(), "모든 레코드 데이터 조회 성공", recordService.getAllRecords());
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "레코드 데이터 조회 (단일)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "레코드 데이터 조회 실패", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "레코드 데이터 조회 성공", content = @Content(mediaType = "application/json"))
    })
    public ResponseObj<List<RecordVO>> getRecordsByUserId(@PathVariable("userId") long id) {
        List<RecordVO> records = recordService.getRecordsByUserId(id);

        if (records.isEmpty()) {
            return ResponseObj.of(HttpStatus.NOT_FOUND.value(), "레코드 데이터 조회 실패");
        }
        return ResponseObj.of(HttpStatus.OK.value(), "레코드 데이터 조회 성공", records);
    }
}
