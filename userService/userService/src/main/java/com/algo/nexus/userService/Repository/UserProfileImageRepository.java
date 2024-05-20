package com.algo.nexus.userService.Repository;

import com.algo.nexus.userService.Model.Entities.User;
import com.algo.nexus.userService.Model.Entities.UserProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, UUID> {


    UserProfileImage findByUser(User user);

}
