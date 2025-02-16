    package com.healthnutri.healthnutrition.service;

    import com.healthnutri.healthnutrition.dto.UserConverter;
    import com.healthnutri.healthnutrition.dto.UserDTO;
    import com.healthnutri.healthnutrition.model.User;
    import com.healthnutri.healthnutrition.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.Collections;
    import java.util.NoSuchElementException;


    @Service
    public class UserService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;
        public User getByEmail(String email){
            return userRepository.findByEmail(email).orElseThrow( () -> new NoSuchElementException("Email not found"));
        }

        public boolean validatePassword(String rawPassword, String encodedPassword) {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        }
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            System.out.println("✅ Usuário encontrado: " + user.getEmail() + " | Senha: " + user.getPassword());

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(Collections.emptyList()) // Add roles/authorities here if applicable
                    .build();
        }
        public UserDTO getById(Long id){
            User user = userRepository.findById(id).
                    orElseThrow( () -> new NoSuchElementException("User Not Found") );
            return UserConverter.userDTO(user);

        }
        //Registro User
        public void createUser(User user){
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Este email já está registrado");
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
