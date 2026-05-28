package com.GyuSoJoo.TypingFantasy.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class MonsterDTO {
    @Data
    public static class AddMonsterRequest {
        private String name;
        private int level;
        private String detail;
        private String jsCode;
        private String javaCode;
        private MultipartFile silhouetteImg;
        private MultipartFile normalImg;
    }

    @Data
    public static class UpdateMonsterRequest {
        private long id;
        private String name;
        private int level;
        private String detail;
        private String jsCode;
        private String javaCode;
        private MultipartFile silhouetteImg;
        private MultipartFile normalImg;
    }
}
