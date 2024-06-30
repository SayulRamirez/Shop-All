package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.UserResponse;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.UserRepository;
import com.metaphorce.shop_all.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        List<UserResponse> response = new ArrayList<>();

        userRepository
                .findAll()
                .forEach(user -> response.add(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.isActive())));

        return response;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setActive(false);

        userRepository.save(user);
    }
}
