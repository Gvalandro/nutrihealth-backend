package com.healthnutri.healthnutrition.service;

import com.healthnutri.healthnutrition.dto.UserConverter;
import com.healthnutri.healthnutrition.dto.UserDTO;
import com.healthnutri.healthnutrition.model.User;
import com.healthnutri.healthnutrition.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow( () -> new NoSuchElementException("Email not found"));
    }

    public UserDTO getById(Long id){
        User user = userRepository.findById(id).
                orElseThrow( () -> new NoSuchElementException("User Not Found") );
        return UserConverter.userDTO(user);

    }

    //Login User
    public UserDTO loginUser(String email, String password){
        User user = this.getByEmail(email);
        if(user != null && passwordEncoder.matches(password,user.getPassword() )){
            return UserConverter.userDTO(user);
        }
        throw new RuntimeException("Invalid Password");
    }

    //Registro User
    public void createUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    //Delete User
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    //Atualizar User
    public void updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(user);
    }
    

}
