package com.authService.Services;
import com.authService.Model.Entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> findByEmail(String email);

    void saveUser(User user);

    User findUser(UUID id);

}
