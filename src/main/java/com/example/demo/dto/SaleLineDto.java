package com.example.demo.dto;

import com.example.demo.entity.SaleLine;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleLineDto {
    private Long id;
    private Integer quantity;
    private Float unitPrice;
    private ProductDto product;

    public static SaleLineDto fromSaleLine(SaleLine saleLine) {
        ProductDto product = new ProductDto(
                saleLine.getProduct().getId(),
                saleLine.getProduct().getName(),
                saleLine.getProduct().getCode()
        );

        return SaleLineDto.builder()
                .id(saleLine.getId())
                .quantity(saleLine.getQuantity())
                .unitPrice(saleLine.getUnitPrice())
                .product(product)
                .build();
    }
}
