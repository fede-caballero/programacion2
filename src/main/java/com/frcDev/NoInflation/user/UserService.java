package com.frcDev.NoInflation.user;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void createUser(User user);
    User getUserById(Long id);
    boolean updateUser(Long id, User updatedUser);
    boolean deleteUserById(Long id);
}
