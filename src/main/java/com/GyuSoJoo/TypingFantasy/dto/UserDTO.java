package com.GyuSoJoo.TypingFantasy.dto;

import java.math.BigDecimal;

public class UserDTO {
    private UserDTO() {}

    public record RegisterRequest(String name, String password) {}

    public record LoginRequest(String name, String password) {}
    public record LoginResponse(Long id, String name, String selectedLang) {}

    public record StatsResponse(String recordTotalErrors, Long totalScore, BigDecimal recordAccuracyAvg, int recordCpmAvg) {}

    public record RankingsResponse(String name, Long totalScore) {}

    public record LangRequest(Long id, String selectedLang) {}
}
