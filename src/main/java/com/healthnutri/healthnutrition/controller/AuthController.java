package com.healthnutri.healthnutrition.controller;

import com.healthnutri.healthnutrition.config.JwtUtils;
import com.healthnutri.healthnutrition.dto.UserConverter;
import com.healthnutri.healthnutrition.dto.UserLoginDTO;
import com.healthnutri.healthnutrition.model.User;
import com.healthnutri.healthnutrition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            User newUser = UserConverter.toEntity(userLoginDTO);

            userService.createUser(newUser);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuário registrado com sucesso");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        UserDetails userDetails = userService.loadUserByUsername(userLoginDTO.getEmail());
        System.out.println("🔍 Usuário carregado: " + userDetails.getUsername());
        System.out.println("🔑 Senha armazenada: " + userDetails.getPassword());
        System.out.println("✅ Senha válida? " + passwordEncoder.matches(userLoginDTO.getPassword(), userDetails.getPassword()));
        try {
            System.out.println("🛠️ Tentando autenticar...");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getEmail(),
                            userLoginDTO.getPassword()
                    )
            );

            System.out.println("✅ Autenticação bem-sucedida!");

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(userLoginDTO.getEmail());

            return ResponseEntity.ok(Collections.singletonMap("token", jwt));
        } catch (Exception e) {
            System.out.println("❌ Erro na autenticação: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Credenciais inválidas.");
        }

    }
}
