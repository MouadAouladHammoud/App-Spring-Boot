package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<List<User>> getAll() {
        return Optional.of(userRepository.findAll());
    }

    public Optional<List<User>> getAll2() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return Optional.of(users);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> save(User user) {
        User userSaved = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge()).
                build();
        return Optional.of(userRepository.save(userSaved));
    }

    public Optional<User> update(User user) {
        Optional<User> user_ = userRepository.findById(user.getId());
        if (user_.isPresent()) {
            User existingUser = user_.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAge(user.getAge());

            userRepository.save(existingUser);
            return Optional.of(existingUser);
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
