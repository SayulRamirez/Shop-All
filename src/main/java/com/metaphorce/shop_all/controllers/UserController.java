package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.domain.UserResponse;
import com.metaphorce.shop_all.services.UserServiceImpl;
import com.metaphorce.shop_all.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.notFound().build();
    }
}
