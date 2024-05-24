package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.domain.UserResponse;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.UserRepository;
import com.metaphorce.shop_all.services.interfaces.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();

        List<UserResponse> response = new ArrayList<>();

        users.forEach(u -> response.add(new UserResponse(u.getId(), u.getName(), u.getEmail(), u.isActive())));

        return response;
    }

    @Override
    public UserResponse register(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EntityExistsException("User already exists");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email()).build();

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.isActive());
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setActive(false);

        userRepository.save(user);
    }
}
