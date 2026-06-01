package com.GyuSoJoo.TypingFantasy.dto;

import java.math.BigDecimal;

public class RecordDTO {
    private RecordDTO() {}

    public record InsertRequest(
            long userId,
            String userName,
            long monsterId,
            String selectedLang,
            int timer,
            BigDecimal accuracy,
            int cpm,
            int specialCharError,
            int caseMismatchError,
            int normalTextError,
            int score
    ) {}

    public record userRecordResponse(
            BigDecimal accuracyAvg,
            int cpmAvg,
            int totalSpecialCharError,
            int totalCaseMismatchError,
            int totalNormalTextError,
            long totalScore
    ) {}
}
