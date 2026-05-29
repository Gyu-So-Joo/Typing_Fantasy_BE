package com.GyuSoJoo.TypingFantasy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class RecordVO {
    private long id;
    private long userId;
    private String userName;
    private String selectedLang;
    private int timer;
    private BigDecimal accuracy;
    private int cpm;
    private int specialCharError;
    private int caseMismatchError;
    private int indentationError;
    private int normalTextError;
    private int score;
    private String createdAt;
}
