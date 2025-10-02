package com.atalaykaan.bankservicebackend.service;

import com.atalaykaan.bankservicebackend.dto.response.UserDTO;
import com.atalaykaan.bankservicebackend.dto.request.create.CreateUserRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateUserRequest;
import com.atalaykaan.bankservicebackend.exception.UserNotFoundException;
import com.atalaykaan.bankservicebackend.mapper.impl.UserMapper;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.User;
import com.atalaykaan.bankservicebackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<UserDTO> findAllUsers() {

        return Optional.of(userRepository.findAll())
                .filter(list -> !list.isEmpty())
                .map(users -> users.stream().map(userMapper::toDTO).toList())
                .orElseThrow(() -> new UserNotFoundException("No users were found"));
    }

    public UserDTO findUserDtoById(Long id) {

        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public UserDTO createUser(CreateUserRequest createUserRequest) {

        User user = User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .birthDate(createUserRequest.getBirthDate())
                .build();

        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UpdateUserRequest updateUserRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        user.setBirthDate(updateUserRequest.getBirthDate());

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    @Transactional
    protected User addAccountToUser(Account account, User user) {

        user.setAccount(account);

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {

        userRepository.findById(id)
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

    protected User findUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}
