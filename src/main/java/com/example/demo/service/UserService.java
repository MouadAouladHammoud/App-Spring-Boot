package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.ErrorCodes;
import com.example.demo.exception.InvalidEntityException;
import com.example.demo.exception.InvalidOperationException;
import com.example.demo.repository.UserRepository;
import com.example.demo.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j // Pour logger =>  log.error()
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

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur avec cet ID = " + id + " n'a été trouvé dans la BDD", ErrorCodes.USER_NOT_FOUND));
    }

    public Optional<User> save(User user) {
        List<String> errors = UserValidator.validate(user);
        if (!errors.isEmpty()) {
            log.error("User is not valid {}", user);
            throw new InvalidEntityException("L'utilisateur est invalide", ErrorCodes.USER_NOT_VALID, errors);
        }

        if(userAlreadyExists(user.getEmail())) {
            throw new InvalidEntityException("Un autre utilisateur avec le même email existe déjà", ErrorCodes.USER_ALREADY_EXISTS, Collections.singletonList("Un autre utilisateur avec le même email existe déjà dans la base de données."));
        }

        User userSaved = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge()).
                build();
        return Optional.of(userRepository.save(userSaved));
    }

    public Optional<User> update(User user) {
        List<String> errors = UserValidator.validate(user);
        if (!errors.isEmpty()) {
            log.error("User is not valid {}", user);
            throw new InvalidEntityException("L'utilisateur est invalide", ErrorCodes.USER_NOT_VALID, errors);
        }

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
        if (id == null) {
            log.warn("Impossible de supprimer un utilisateur avec un ID nul");
            throw new InvalidOperationException("Aucun ID fourni pour l'utilisateur concerné", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if(!userAlreadyExists(id)) {
            throw new InvalidOperationException("Impossible de supprimer cet utilisateur, aucun utilisateur trouvé avec l'ID fourni", ErrorCodes.USER_NOT_FOUND);
        }

        userRepository.deleteById(id);
        return true;
    }

    private boolean userAlreadyExists(Object value) {
        Optional<User> user;

        if (value instanceof String) {
            user = userRepository.findByEmail((String) value);
        } else if (value instanceof Long) {
            user = userRepository.findById((Long) value);
        } else {
            throw new IllegalArgumentException("Invalid parameter type. Expected String or Long.");
        }

        return user.isPresent();
    }
}
