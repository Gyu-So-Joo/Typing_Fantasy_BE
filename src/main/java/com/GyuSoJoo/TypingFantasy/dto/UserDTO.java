package com.GyuSoJoo.TypingFantasy.dto;

import java.math.BigDecimal;

public class UserDTO {
    private UserDTO() {}

    public record RegisterRequest(String name, String password) {}

    public record LoginRequest(String name, String password) {}
    public record LoginResponse(Long id, String name, String monsterIds, String selectedLang) {}

    public record StatsResponse(String recordTotalErrors, Long totalScore, BigDecimal recordAccuracyAvg, int recordCpmAvg) {}
}
