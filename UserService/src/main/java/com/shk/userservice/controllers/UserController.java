package com.shk.userservice.controllers;

import com.shk.userservice.dtos.UserResponseDto;
import com.shk.userservice.models.User;
import com.shk.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //Get Single User
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getSingleUser(@PathVariable String userId){
        UserResponseDto userResponseDto = userService.getUser(userId);
        return ResponseEntity.ok(userResponseDto);
    }


    //Get All Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser =  userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    // Update User
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUserData) {
        // Use the updateUser method from your userServices to update the user
        User updatedUser = userService.updateUser(userId, updatedUserData);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            // User not found
            return ResponseEntity.notFound().build();
        }
    }



    // Delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        // Check if the user exists
        UserResponseDto userToDelete = userService.getUser(userId);

        if (userToDelete != null) {
            // Delete the user using your userServices
            userService.deleteUser(userToDelete);

            return ResponseEntity.noContent().build();
        } else {
            // User not found
            return ResponseEntity.notFound().build();
        }
    }
}
