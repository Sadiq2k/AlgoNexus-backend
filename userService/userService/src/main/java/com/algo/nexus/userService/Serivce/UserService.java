package com.algo.nexus.userService.Serivce;

import com.algo.nexus.userService.Model.Request.*;
import com.algo.nexus.userService.Model.Entities.User;
import com.algo.nexus.userService.Model.Response.SocialMediaAllUserResponse;
import com.algo.nexus.userService.Model.Response.UpdateFullNameResponse;
import com.algo.nexus.userService.Model.Response.UpdateUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {

    ResponseEntity<String> registerUser(NewUserRequest newUserRequest);
    User getUser(UUID userId);
    Page<User> getAllUser(Integer page, Integer size);

    ResponseEntity<String> updateUser(UpdateUserResponse updateUserResponse);

    Optional<User> getUserByEmail(String email);

    ResponseEntity<UpdateFullNameResponse> updateFullName(FullNameRequest fullNameRequest);

    ResponseEntity<String> addGender(AddGenderRequest addGenderRequest);

    ResponseEntity<String> addLocation(AddLocationRequest addLocationRequest);

    ResponseEntity<String> addBirthDate(AddBirthRequest addBirthRequest);

    ResponseEntity<String> addGithub(AddGithubRequest addGithubRequest);

    ResponseEntity<String> addLinkedIn(AddLinkedInRequest addLinkedInRequest);

    ResponseEntity<String> addWork(AddWorkRequest addWorkRequest);


    Long getUserCount();

    Page<User> getAllUserForMyNetworkPage(Integer page, Integer size);
}
