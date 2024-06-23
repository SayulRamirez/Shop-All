package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.domain.UserResponse;
import com.metaphorce.shop_all.services.UserServiceImpl;
import com.metaphorce.shop_all.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @Operation(summary = "List all active and inactive users", description = "It returns all users, whether they are active or not, and if there are none, it will return an empty list")
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Register a new user", description = "Register a new user and return the registered user, if the user already has the email registered an error will be thrown",
            responses = {@ApiResponse(description = "If the data sent is not correctly formulated", responseCode = "400", content = @Content),
                    @ApiResponse(description = "If the user already exists in the database", responseCode = "409", content = @Content),
                    @ApiResponse(description = "If the user not exists in the database and the register is successful", responseCode = "200")
            })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @Operation(summary = "Inactivate a user", description = "It eliminates or rather makes the user inactive by means of their id, if it does not find it it will throw an error.",
            responses = {@ApiResponse(description = "If the data sent is not correctly formulated", responseCode = "400"),
                    @ApiResponse(description = "If the user is successfully unsubscribed", responseCode = "404")
            })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                               @PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.notFound().build();
    }
}
