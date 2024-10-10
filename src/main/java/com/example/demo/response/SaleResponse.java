package com.example.demo.response;

import java.util.Date;
import java.util.List;

public record SaleResponse(
    Long id,
    String reference,
    Date date,
    List<SaleLineResponse> saleLines
) {}