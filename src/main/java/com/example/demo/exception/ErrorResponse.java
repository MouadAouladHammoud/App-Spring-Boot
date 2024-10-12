package com.example.demo.exception;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private ErrorCodes code;
    private Integer httpCode;
    private String message;
    private List<String> errors = new ArrayList<>();
}
