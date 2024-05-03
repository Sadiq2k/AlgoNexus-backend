package com.algo.nexus.userService.Serivce;

import com.algo.nexus.userService.Entities.User;
import com.algo.nexus.userService.Request.*;
import com.algo.nexus.userService.Response.UpdateFullNameResponse;
import com.algo.nexus.userService.Response.UpdateUserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {

    ResponseEntity<String> registerUser(NewUserRequest newUserRequest);
    User getUser(UUID userId);
    List<User> getAllUser();

    ResponseEntity<String> updateUser(UpdateUserResponse updateUserResponse);

    Optional<User> getUserByEmail(String email);

    ResponseEntity<UpdateFullNameResponse> updateFullName(FullNameRequest fullNameRequest);

    ResponseEntity<String> addGender(AddGenderRequest addGenderRequest);

    ResponseEntity<String> addLocation(AddLocationRequest addLocationRequest);

    ResponseEntity<String> addBirthDate(AddBirthRequest addBirthRequest);

    ResponseEntity<String> addGithub(AddGithubRequest addGithubRequest);

    ResponseEntity<String> addLinkedIn(AddLinkedInRequest addLinkedInRequest);

    ResponseEntity<String> addWork(AddWorkRequest addWorkRequest);


}
