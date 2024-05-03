package com.algo.nexus.userService.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfileImage {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;
    private String name;
    private String imageUrl;
    private String imageId;

    // Define Many-to-One relationship with User entity
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    public UserProfileImage(String name, String imageUrl, String imageId, User user) {
//        this.name = name;
//        this.imageUrl = imageUrl;
//        this.imageId = imageId;
//        this.user = user;
//    }


}
