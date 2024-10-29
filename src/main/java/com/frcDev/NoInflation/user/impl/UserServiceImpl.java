package com.frcDev.NoInflation.user.impl;

import com.frcDev.NoInflation.dto.UserLoginDto;
import com.frcDev.NoInflation.user.User;
import com.frcDev.NoInflation.user.UserRepository;
import com.frcDev.NoInflation.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateUser(Long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            //user.setRole(updatedUser.getRole());
            userRepository.save(user);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User registerUser(User user) {
        // Validar email único
        if (userRepository.findByEmail(user.getEmail().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        try {
            // Normalizar email
            user.setEmail(user.getEmail().toLowerCase().trim());

            // Si es comercio, establecer la relación bidireccional
            if ("COMMERCE".equals(user.getRole()) && user.getShop() != null) {
                user.getShop().setUser(user);
            }

            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public User loginUser(UserLoginDto loginDto) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginDto.getEmail().toLowerCase());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error in loginUser service: " + e.getMessage());
            throw e;
        }
    }


    @Override
    public User findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElse(null);
    }
}
