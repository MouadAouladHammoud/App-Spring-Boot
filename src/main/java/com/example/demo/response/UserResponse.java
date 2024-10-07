package com.example.demo.response;

public record UserResponse(
        Long id,
        String name,
        String email,
        Integer Age
) {
}
