package com.healthnutri.healthnutrition.controller;

import com.healthnutri.healthnutrition.dto.UserConverter;
import com.healthnutri.healthnutrition.dto.UserDTO;
import com.healthnutri.healthnutrition.dto.UserLoginDTO;
import com.healthnutri.healthnutrition.model.User;
import com.healthnutri.healthnutrition.repository.UserRepository;
import com.healthnutri.healthnutrition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/userId")
    public UserDTO getUser(@PathVariable Long id){
        return userService.getById(id);

    }
    public UserDTO updateUser(Long id, UserLoginDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            userRepository.save(user);
            return UserConverter.userDTO(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
