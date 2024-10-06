package com.example.demo.controller;

import com.example.demo.entity.User;
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
    public ResponseEntity<Optional<List<User>>> getAll() {
        Optional<List<User>> users = userService.getAll();
        if (users.isPresent() && !users.get().isEmpty()) {
           return ResponseEntity.ok(users); // 200 OK avec la liste des utilisateurs
        } else {
           return ResponseEntity.noContent().build(); // 204 No Content si la liste est vide ou absente
        }
    }

    @GetMapping("/get-all-2")
    public Optional<List<User>> getAll2() {
        return userService.getAll();
    }

    @GetMapping("/{id_}")
    public ResponseEntity<Optional<User>> findById(@PathVariable("id_") Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Optional<User>> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping
    public ResponseEntity<Optional<User>> update(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build(); // Retourne un status 204 No Content
    }

}
