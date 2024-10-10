package com.example.demo.mapper;

import com.example.demo.entity.*;
import com.example.demo.request.UserRequest;
import com.example.demo.response.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserMapper {

    private SaleLineResponse fromSaleLine(SaleLine saleLine) {
        ProductResponse productResponse = null;
        if (saleLine.getProduct() != null) {
            Product product = saleLine.getProduct();
            productResponse = new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getCode()
            );
        }

        return new SaleLineResponse(
                saleLine.getId(),
                saleLine.getQuantity(),
                saleLine.getUnitPrice(),
                productResponse
        );
    }

    private SaleResponse fromSale(Sale sale) {
        List<SaleLineResponse> saleLineResponses = null;
        if (sale.getSaleLines() != null) {
            saleLineResponses = sale.getSaleLines().stream()
                    .map(this::fromSaleLine)
                    .collect(Collectors.toList());
        }

        return new SaleResponse(
                sale.getId(),
                sale.getReference(),
                sale.getDate(),
                saleLineResponses
        );
    }

    public UserResponse fromUser(User user) {
        CompteResponse compteResponse = null;
        if (user.getCompte() != null) {
            Compte compte = user.getCompte();
            compteResponse = new CompteResponse(
                    compte.getId(),
                    compte.getReference()
            );
        }

        List<SaleResponse> saleResponses = null;
        if (user.getSales() != null) {
            saleResponses = user.getSales().stream()
                    .map(this::fromSale)
                    .collect(Collectors.toList());
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                compteResponse,
                saleResponses
        );
    }

    public User toUser(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.id())
                .name(userRequest.name())
                .email(userRequest.email())
                .age(userRequest.age())
                .build();
    }
}
