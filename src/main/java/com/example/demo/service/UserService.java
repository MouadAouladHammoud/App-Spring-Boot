package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<List<UserResponse>> getAll() {
        /*
        List<User> users = userRepository.findAll();
        // return Optional.of(users); // public  Optional<List<User>> getAll() {...}
        return Optional.of(
                users
                .stream()
                .map(userMapper::fromUser)
                .collect(Collectors.toList())
        );
        */

        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            userResponses.add(userMapper.fromUser(user));
        }
        return Optional.of(userResponses);
    }

    public UserResponse findById(Long id) {
        /*
        // UserResponse user = userRepository.findById(id).map(userMapper::fromUser).orElse(null);
        User u = userRepository.findById(id).orElse(null);
        UserResponse user2 = (u != null) ? userMapper.fromUser(u) : null;
        */

        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return userMapper.fromUser(user);
        }
        return null;
    }

    public UserResponse save(UserRequest userRequest) {
        User userSaved = User.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .age(userRequest.age()).
                build();
        User user = userRepository.save(userSaved);
        return userMapper.fromUser(user);
    }

    public Optional<UserResponse> update(UserRequest userRequest) {
        Optional<User> user_ = userRepository.findById(userRequest.id());
        if (user_.isPresent()) {
            User existingUser = user_.get();
            existingUser.setName(userRequest.name());
            existingUser.setEmail(userRequest.email());
            existingUser.setAge(userRequest.age());
            userRepository.save(existingUser);
            return Optional.of(userMapper.fromUser(existingUser));
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
