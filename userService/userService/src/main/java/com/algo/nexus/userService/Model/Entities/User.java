package com.algo.nexus.userService.Model.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    private UUID id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String github;
    private String linkedin;
    private String work;
    private String education;
    private String location;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProfileImage> profileImages = new ArrayList<>();


}
