package com.GyuSoJoo.TypingFantasy.dto;

public class MonsterDTO {
    private MonsterDTO() {}

    public record AddMonsterRequest(String name, int level, String detail, String jsCode, String javaCode) {}
}
