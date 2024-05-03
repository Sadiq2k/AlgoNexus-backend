package com.algo.nexus.userService.Repository;

import com.algo.nexus.userService.Entities.User;
import com.algo.nexus.userService.Entities.UserProfileImage;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, UUID> {


    UserProfileImage findByUser(User user);

}
