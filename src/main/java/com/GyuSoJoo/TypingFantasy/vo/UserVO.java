package com.GyuSoJoo.TypingFantasy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserVO {
    private Long id;
    private String name;
    private String password;
    private String monsterIds;
    private String selectedLang;
    private Long totalScore;
    private String createdAt;
}
