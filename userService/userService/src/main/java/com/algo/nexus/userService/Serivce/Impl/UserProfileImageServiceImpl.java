package com.algo.nexus.userService.Serivce.Impl;

import com.algo.nexus.userService.Model.Entities.User;
import com.algo.nexus.userService.Model.Entities.UserProfileImage;
import com.algo.nexus.userService.Repository.UserProfileImageRepository;
import com.algo.nexus.userService.Serivce.UserProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileImageServiceImpl implements UserProfileImageService {

    @Autowired
    private UserProfileImageRepository userProfileImageRepository;

    @Override
    public void saveUserProfileImage(UserProfileImage userProfileImage) {
        userProfileImageRepository.save(userProfileImage);

    }

    @Override
    public UserProfileImage findByUser(User user) {
        final UserProfileImage byUser = userProfileImageRepository.findByUser(user);

        return byUser;
    }

    @Override
    public void delete(UUID id) {
        userProfileImageRepository.deleteById(id);
    }
}
