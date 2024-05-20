package com.algo.nexus.userService.Serivce;

import com.algo.nexus.userService.Model.Entities.User;
import com.algo.nexus.userService.Model.Entities.UserProfileImage;

import java.util.UUID;

public interface UserProfileImageService {

    void saveUserProfileImage(UserProfileImage userProfileImage);

    UserProfileImage findByUser(User user);

    void delete(UUID id);

}
