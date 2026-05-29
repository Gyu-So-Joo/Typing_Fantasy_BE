package com.GyuSoJoo.TypingFantasy.dto;

import java.math.BigDecimal;

public class RecordDTO {
    private RecordDTO() {}

    public record InsertRequest(
            long userId,
            String userName,
            String selectedLang,
            int timer,
            BigDecimal accuracy,
            int cpm,
            int specialCharError,
            int caseMismatchError,
            int indentationError,
            int normalTextError,
            int score
    ) {}
}
