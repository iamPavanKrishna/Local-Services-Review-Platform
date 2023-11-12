package com.shk.userservice.services;

import com.shk.userservice.dtos.UserResponseDto;
import com.shk.userservice.models.User;
import com.shk.userservice.exceptions.NotFoundException;
import com.shk.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDto getUser(String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()-> new NotFoundException("User Not Found!"));
        return UserResponseDto.builder()
                .userId(user.getUserId().toString())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        User existingUser = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new NotFoundException("User Not Found"));
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UserResponseDto userToDelete) {
        userRepository.deleteById(UUID.fromString(userToDelete.getUserId()));
    }
}