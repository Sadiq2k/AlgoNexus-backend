package com.algo.nexus.userService.Model.Response;


import com.algo.nexus.userService.Model.Entities.UserProfileImage;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMediaAllUserResponse {


    private UUID id;
    private String firstname;
    private String lastname;
    private String work;
    private String education;
    @JsonManagedReference
    private UserProfileImage profileImage;

}
