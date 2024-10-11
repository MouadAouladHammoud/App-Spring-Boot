package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private Integer age;

    private CompteDto compte;
    private List<SaleDto> sales;

    public static UserDto fromUser(User user) {
        CompteDto compte = new CompteDto(user.getCompte().getId(), user.getCompte().getReference());
        List<SaleDto> sales = user.getSales().stream()
                                                .map(SaleDto::fromSale)
                                                .toList();

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .compte(compte)
                .sales(sales)
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .age(userDto.getAge())
                .build();
    }
}
