package com.example.demo.response;

public record SaleLineResponse(
    Long id,
    Integer quantity,
    Float unitPrice,
    ProductResponse product
) {}
