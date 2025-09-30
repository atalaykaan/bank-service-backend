package com.atalaykaan.bankservicebackend.service.impl;

import com.atalaykaan.bankservicebackend.dto.request.create.CreateUserRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateUserRequest;
import com.atalaykaan.bankservicebackend.exception.UserNotFoundException;
import com.atalaykaan.bankservicebackend.model.User;
import com.atalaykaan.bankservicebackend.repository.UserRepository;
import com.atalaykaan.bankservicebackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {

        return Optional.of(userRepository.findAll())
                .orElseThrow(() -> new UserNotFoundException("No users were found"));
    }

    public User findUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public User createUser(CreateUserRequest createUserRequest) {

        User user = User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .birthDate(createUserRequest.getBirthDate())
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, UpdateUserRequest updateUserRequest) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        user.setBirthDate(updateUserRequest.getBirthDate());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {

        userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllUsers() {

        if(userRepository.findAll().isEmpty()) {

            throw new UserNotFoundException("No users were found");
        }

        userRepository.deleteAll();
    }
}
