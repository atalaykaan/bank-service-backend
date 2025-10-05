package com.atalaykaan.bankservicebackend.service;

import com.atalaykaan.bankservicebackend.dto.request.create.CreateUserRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateUserRequest;
import com.atalaykaan.bankservicebackend.dto.response.UserDTO;
import com.atalaykaan.bankservicebackend.exception.UserNotFoundException;
import com.atalaykaan.bankservicebackend.mapper.impl.UserMapper;
import com.atalaykaan.bankservicebackend.model.User;
import com.atalaykaan.bankservicebackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void whenCreatingSingleUser_thenReturnBackUserDto() {

        LocalDate birthDate = LocalDate.parse("13/04/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setName("John");
        createUserRequest.setBirthDate(birthDate);

        User user = User.builder()
                .name("John")
                .birthDate(birthDate)
                .build();

        User userResult = User.builder()
                .id(1L)
                .name("John")
                .birthDate(birthDate)
                .build();

        UserDTO expectedUserDto = UserDTO.builder()
                .id(1L)
                .name("John")
                .birthDate(birthDate)
                .build();

        Mockito.when(userRepository.save(user)).thenReturn(userResult);

        Mockito.when(userMapper.toDTO(userResult)).thenReturn(expectedUserDto);

        assertEquals(expectedUserDto, userService.createUser(createUserRequest));
    }

    @Test
    void whenUpdatingUser_thenReturnBackUserDto() {

        LocalDate oldBirthDate = LocalDate.parse("13/04/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        User oldUser = User.builder()
                .id(1L)
                .name("Mary")
                .birthDate(oldBirthDate)
                .build();

        LocalDate newBirthDate = LocalDate.parse("20/08/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        updateUserRequest.setName("Test");
        updateUserRequest.setBirthDate(newBirthDate);

        User updatedUser = User.builder()
                .id(1L)
                .name(updateUserRequest.getName())
                .birthDate(updateUserRequest.getBirthDate())
                .build();

        UserDTO expectedUserDto = UserDTO.builder()
                .id(1L)
                .name(updateUserRequest.getName())
                .birthDate(updateUserRequest.getBirthDate())
                .build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));

        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        Mockito.when(userMapper.toDTO(updatedUser)).thenReturn(expectedUserDto);

        assertEquals(expectedUserDto, userService.updateUser(1L, updateUserRequest));
    }

    @Test
    void whenDeletingUserWithInvalidId_thenThrowException() {

        LocalDate birthDate = LocalDate.parse("30/05/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setName("John");

        createUserRequest.setBirthDate(birthDate);

        User user = User.builder()
                .name(createUserRequest.getName())
                .birthDate(createUserRequest.getBirthDate())
                .build();

        User resultUser = User.builder()
                .id(1L)
                .name(createUserRequest.getName())
                .birthDate(createUserRequest.getBirthDate())
                .build();

        UserDTO expectedDto = UserDTO.builder()
                .id(1L)
                .name(createUserRequest.getName())
                .birthDate(createUserRequest.getBirthDate())
                .build();

        Mockito.when(userRepository.save(user)).thenReturn(resultUser);

        Mockito.when(userMapper.toDTO(resultUser)).thenReturn(expectedDto);

        assertEquals(expectedDto, userService.createUser(createUserRequest));


        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(resultUser));

        assertEquals(resultUser, userService.findUserById(1L));

        Mockito.when(userRepository.findById(2L)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(2L));
    }

}