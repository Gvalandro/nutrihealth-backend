package com.healthnutri.healthnutrition.controller;

import com.healthnutri.healthnutrition.dto.UserConverter;
import com.healthnutri.healthnutrition.dto.UserLoginDTO;
import com.healthnutri.healthnutrition.model.User;
import com.healthnutri.healthnutrition.repository.UserRepository;
import com.healthnutri.healthnutrition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam UserLoginDTO userLoginDTO) {
        if (userRepository.findByEmail(userLoginDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setEmail(userLoginDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userLoginDTO.getPassword()));
        userService.createUser(user);
        return ResponseEntity.ok("User registred successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(userRepository.findByEmail(email).isPresent()){
            User user = userRepository.findByEmail(email).get();
            return ResponseEntity.ok(UserConverter.userDTO(user));
        }
        return ResponseEntity.badRequest().body("Login is Wrong");
    }
}
