package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Optional<List<UserResponse>>> getAll() {
        Optional<List<UserResponse>> users = userService.getAll();
        if (users.isPresent() && !users.get().isEmpty()) {
           return ResponseEntity.ok(users); // 200 OK avec la liste des utilisateurs
        } else {
           return ResponseEntity.noContent().build(); // 204 No Content si la liste est vide ou absente
        }
    }

    @GetMapping("/{id_}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id_") Long id) {
        UserResponse user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Optional<UserResponse>> save(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(Optional.of(userService.save(userRequest)));
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.update(userRequest).orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build(); // Retourne un status 204 No Content
    }

}
