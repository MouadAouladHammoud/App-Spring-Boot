package com.example.demo.response;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        Integer age,
        CompteResponse compte,
        List<SaleResponse> sales
) {}