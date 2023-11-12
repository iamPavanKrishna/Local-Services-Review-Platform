package com.shk.userservice.services;

import com.shk.userservice.dtos.UserResponseDto;
import com.shk.userservice.models.User;

import java.util.List;

public interface UserService {
    //User Operations

    //Create User
    //User saveUser(User user);



    //Get All User
    List<User> getAllUser();

    //Get Single User of Given id
    UserResponseDto getUser(String userId);

    // Update User
    User updateUser(String userId, User updatedUser);

    //Delete User
    void deleteUser(UserResponseDto userToDelete);
}
