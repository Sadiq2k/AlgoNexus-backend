package com.algo.nexus.userService.Model.Entities;

import com.algo.nexus.userService.Model.Entities.User;
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

//    @JsonBackReference
//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = true)
//    private User user;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;


}
