package com.algo.nexus.userService.Controller;

import com.algo.nexus.userService.Model.Request.*;
import com.algo.nexus.userService.Model.Entities.User;
import com.algo.nexus.userService.FiegnClient.AuthenticationFeignClient;
import com.algo.nexus.userService.Model.Response.UpdateFullNameResponse;
import com.algo.nexus.userService.Model.Response.UpdateUserResponse;
import com.algo.nexus.userService.Model.Response.UserResponse;
import com.algo.nexus.userService.Serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationFeignClient authenticationFeignClient;
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody NewUserRequest newUserRequest) {
        final ResponseEntity<String> stringResponseEntity = userService.registerUser(newUserRequest);
        return stringResponseEntity;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUser(){
        List<User> users = userService.getAllUser();
        List<UserResponse> userResponses = users.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());

                    userResponse.setFirstname(user.getFirstname());
                    userResponse.setLastname(user.getLastname());
                    userResponse.setEmail(user.getEmail());
                    boolean userBlocked = authenticationFeignClient.isUserBlocked(user.getId());
                    String roles = authenticationFeignClient.getRoles(user.getId());
                    roles = roles.replaceAll("[\\[\\]]", "").replaceAll(", ", ",");
                    userResponse.setRole(roles);
                    userResponse.setBlock(userBlocked);

                    return userResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserResponse updateUserResponse){
             ResponseEntity<String> response = userService.updateUser(updateUserResponse);
        return response;
    }

    @GetMapping({"/logedUser/{userId}"})
    public User getLoginUser(@PathVariable UUID userId){
        User user = userService.getUser(userId);

            if (user != null){
                return  user;
            }
        return user;
    }

    @PostMapping("/updateName")
    public ResponseEntity<UpdateFullNameResponse> setFullName(@RequestBody FullNameRequest fullNameRequest){
        System.out.println("update name");
        final ResponseEntity<UpdateFullNameResponse> updateFullNameResponseResponseEntity = userService.updateFullName(fullNameRequest);
       return updateFullNameResponseResponseEntity;

    }

    @PostMapping("/add/gender")
    public ResponseEntity<String> setGender(@RequestBody AddGenderRequest addGenderRequest){
         ResponseEntity<String> response = userService.addGender(addGenderRequest);
        return response;
    }

    @PostMapping("/add/location")
    public ResponseEntity<String> setLocation(@RequestBody AddLocationRequest addLocationRequest){
        ResponseEntity<String> response = userService.addLocation(addLocationRequest);
        return response;
    }

    @PostMapping("/add/birth")
    public ResponseEntity<String> setBirthday(@RequestBody AddBirthRequest addBirthRequest){
        ResponseEntity<String> response = userService.addBirthDate(addBirthRequest);
        return response;
    }

    @PostMapping("/add/github")
    public ResponseEntity<String> setGithub(@RequestBody AddGithubRequest addGithubRequest){
        ResponseEntity<String> response = userService.addGithub(addGithubRequest);
        return response;
    }
    @PostMapping("/add/linkedin")
    public ResponseEntity<String> setLinkedIn(@RequestBody AddLinkedInRequest addLinkedInRequest){
        ResponseEntity<String> response = userService.addLinkedIn(addLinkedInRequest);
        return response;
    }

    @PostMapping("/add/work")
    public ResponseEntity<String> setWork(@RequestBody AddWorkRequest addWorkRequest){
        ResponseEntity<String> response = userService.addWork(addWorkRequest);
        return response;
    }







}
