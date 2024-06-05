package com.algo.nexus.userService.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedSocialUserResponse {

    private List<SocialMediaAllUserResponse> users;
    private int totalPages;
    private long totalElements;
}
