package com.frcDev.NoInflation.user;

import com.frcDev.NoInflation.dto.UserLoginDto;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User getUserById(Long id);
    boolean updateUser(Long id, User updatedUser);
    boolean deleteUserById(Long id);

    User registerUser(User user);

    User loginUser(UserLoginDto loginDto);

    User findByEmail(String userEmail);
}
