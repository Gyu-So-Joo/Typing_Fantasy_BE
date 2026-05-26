package com.GyuSoJoo.TypingFantasy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MonsterVO {
    private Long id;
    private String name;
    private int level;
    private String detail;
    private String jsCode;
    private String javaCode;
    private String silhouetteImg;
    private String normalImg;
    private String createdAt;
}
