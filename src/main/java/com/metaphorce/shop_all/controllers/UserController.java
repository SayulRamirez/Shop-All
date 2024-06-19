package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.domain.UserResponse;
import com.metaphorce.shop_all.services.UserServiceImpl;
import com.metaphorce.shop_all.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "Lista todos los usuarios activos e inactivos", description = "Devuelve todos los usuarios, esten o no esten activos y si no hay, devolverá una lista vacía")
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Registra un nuevo usuario", description = "Registra un nuevo usuario y devolvera el usuario registrado, si el usuario ya tiene registrado el email se lanzara un error")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @Operation(summary = "Inactiva un usuario", description = "Elimina o mejor dicho pone en inactivo al usuario mediante su id, si no lo encuetra lanzara un error.")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                               @PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.notFound().build();
    }
}
