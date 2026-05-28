package com.GyuSoJoo.TypingFantasy.dto;

import java.math.BigDecimal;
import java.util.Map;

public class UserDTO {
    private UserDTO() {}

    public record RegisterRequest(String name, String password) {}

    public record LoginRequest(String name, String password) {}
    public record LoginResponse(Long id, String name, String monsterIds, String selectedLang) {}

    public record StatsResponse(Map<String, Integer> recordTotalErrors, Long totalScore, BigDecimal recordAccuracyAvg, int recordCpmAvg) {}
}
