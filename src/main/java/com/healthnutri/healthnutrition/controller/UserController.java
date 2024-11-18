package com.healthnutri.healthnutrition.controller;

import com.healthnutri.healthnutrition.dto.UserConverter;
import com.healthnutri.healthnutrition.dto.UserDTO;
import com.healthnutri.healthnutrition.dto.UserLoginDTO;
import com.healthnutri.healthnutrition.repository.UserRepository;
import com.healthnutri.healthnutrition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id){
        return userService.getById(id);

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserLoginDTO userDTO) {
        try {
            userService.updateUser(id, UserConverter.toEntity(userDTO));
            return ResponseEntity.ok("User updated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
