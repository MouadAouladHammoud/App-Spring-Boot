package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<List<UserDto>> getAll() {
        /*
        List<User> users = userRepository.findAll();
        // return Optional.of(users); // public  Optional<List<User>> getAll() {...}
        return Optional.of(
                users
                .stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList())
        );
        */

        List<UserDto> usersDto = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            usersDto.add(UserDto.fromUser(user));
        }
        return Optional.of(usersDto);
    }

    public UserDto findById(Long id) {
        /*
        // UserDto user = userRepository.findById(id).map(UserDto::fromUser).orElse(null);
        User u = userRepository.findById(id).orElse(null);
        UserDto user2 = (u != null) ? UserDto.fromUser(u) : null;
        return user2;
        */

        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return UserDto.fromUser(user);
        }
        return null;
    }

    public UserDto save(UserDto userDto_) {
        User user = userRepository.save(UserDto.toUser(userDto_));
        return UserDto.fromUser(user);
    }

    public Optional<UserDto> update(UserDto userDto_) {
        Optional<User> user_ = userRepository.findById(userDto_.getId());
        if (user_.isPresent()) {
            User existingUser = user_.get();
            existingUser.setName(userDto_.getName());
            existingUser.setEmail(userDto_.getEmail());
            existingUser.setAge(userDto_.getAge());
            userRepository.save(existingUser);
            return Optional.of(UserDto.fromUser(existingUser));
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
