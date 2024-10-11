package com.example.demo.dto;

import com.example.demo.entity.Sale;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class SaleDto {
    private Long id;
    private String reference;
    private Date date;
    private List<SaleLineDto> saleLines;

    public static SaleDto fromSale(Sale sale) {
        List<SaleLineDto> saleLines = sale.getSaleLines().stream()
                                                    .map(SaleLineDto::fromSaleLine)
                                                    .toList();

        return SaleDto.builder()
                .id(sale.getId())
                .reference(sale.getReference())
                .date(sale.getDate())
                .saleLines(saleLines)
                .build();

    }
}
