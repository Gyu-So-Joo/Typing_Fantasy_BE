package com.GyuSoJoo.TypingFantasy.dto;

public record ResponseObj<T>(
        int status,
        String message,
        T data
) {
    public static <T> ResponseObj<T> of(int status, String message) {
        return new ResponseObj<>(status, message, null);
    }

    public static <T> ResponseObj<T> of(int status, String message, T data) {
        return new ResponseObj<>(status, message, data);
    }
}
