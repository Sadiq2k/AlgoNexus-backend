package com.algo.nexus.userService.Controller;

import com.algo.nexus.userService.Model.Entities.UserProfileImage;
import com.algo.nexus.userService.Model.Request.*;
import com.algo.nexus.userService.Model.Entities.User;
import com.algo.nexus.userService.FiegnClient.AuthenticationFeignClient;
import com.algo.nexus.userService.Model.Response.*;
import com.algo.nexus.userService.Serivce.UserProfileImageService;
import com.algo.nexus.userService.Serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationFeignClient authenticationFeignClient;
    @Autowired
    private UserProfileImageService userProfileImageService;
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody NewUserRequest newUserRequest) {
        final ResponseEntity<String> stringResponseEntity = userService.registerUser(newUserRequest);
        return stringResponseEntity;
    }

    @GetMapping("/users")
    public ResponseEntity<PagedResponse<UserResponse>> getAllUser(@RequestParam(value = "page",defaultValue = "0",required = false)Integer page,
                                                                  @RequestParam(value = "size",defaultValue = "10",required = false)Integer size) {
        Page<User> users = userService.getAllUser(page, size);

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

        PagedResponse<UserResponse> response = new PagedResponse<>();
        response.setContent(userResponses);
        response.setPage(users.getNumber());
        response.setSize(users.getSize());
        response.setTotalElements(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());
        response.setLast(users.isLast());

        return ResponseEntity.ok(response);
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

    @GetMapping("/getAllUsersCount")
    public Long getUserCount(){
      return  userService.getUserCount();
    }


    @GetMapping("/all/social-media")
    public PaginatedSocialUserResponse getAllUserForMyNetworkPage(@RequestParam(value = "page",defaultValue = "0",required = false)Integer page,
                                                                       @RequestParam(value = "size",defaultValue = "10",required = false)Integer size){
        Page<User> allUserForMyNetworkPage = userService.getAllUserForMyNetworkPage(page, size);
        List<SocialMediaAllUserResponse> userResponses = allUserForMyNetworkPage.stream()
                .map(user -> {
                    UserProfileImage profileImage = userProfileImageService.findByUser(user);
                    return new SocialMediaAllUserResponse(
                            user.getId(),
                            user.getFirstname(),
                            user.getLastname(),
                            user.getEducation(),
                            user.getWork(),
                            profileImage
                    );
                })
                .collect(Collectors.toList());

        return new PaginatedSocialUserResponse(
                userResponses,
                allUserForMyNetworkPage.getTotalPages(),
                allUserForMyNetworkPage.getTotalElements()
        );
    }


}
