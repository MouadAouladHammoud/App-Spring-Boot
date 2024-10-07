package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.id())
                .name(userRequest.name())
                .email(userRequest.email())
                .age(userRequest.age())
                .build();
    }

    public UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }

}
