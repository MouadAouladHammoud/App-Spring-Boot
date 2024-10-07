package com.example.demo.request;

public record UserRequest(
        Long id,
        String name,
        String email,
        Integer age
) {
}
