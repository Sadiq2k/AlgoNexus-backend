package com.algo.nexus.userService.Serivce.Impl;

import com.algo.nexus.userService.Model.Request.*;
import com.algo.nexus.userService.Model.Entities.User;
import com.algo.nexus.userService.Repository.UserRepository;
import com.algo.nexus.userService.Model.Response.UpdateFullNameResponse;
import com.algo.nexus.userService.Model.Response.UpdateUserResponse;
import com.algo.nexus.userService.Serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseEntity<String> registerUser(NewUserRequest newUserRequest) {
        if(userRepository.existsByEmail(newUserRequest.getEmail())){
            System.out.println(newUserRequest.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A user is already registered with this e-mail address.");
        }
        
        User user = new User();
        user.setId(newUserRequest.getId());
        user.setFirstname(newUserRequest.getFirstname());
        user.setLastname(newUserRequest.getLastname());
        user.setEmail(newUserRequest.getEmail());
        user.setCreatedAccountDate(LocalDateTime.now());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }
    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId);
    }



    @Override
    public  Page<User> getAllUser(Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<User> pageCourse = userRepository.findAll(pageable);
        List<User> allTopics = pageCourse.getContent();
        Page<User> responsePage = new PageImpl<>(allTopics, pageable, pageCourse.getTotalElements());
        return responsePage;
    }

    @Override
    public ResponseEntity<String> updateUser(UpdateUserResponse updateUserResponse) {
        System.out.println("user update");
        User user = new User();
        user.setId(updateUserResponse.getId());
        user.setFirstname(updateUserResponse.getFirstname());
        user.setLastname(updateUserResponse.getLastname());
        user.setEmail(updateUserResponse.getEmail());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User update successfully");
    }

    @Override
    public Optional<User> getUserByEmail(String name) {
        return userRepository.findUserByEmail(name);
    }

    @Override
    public ResponseEntity<UpdateFullNameResponse> updateFullName(@RequestBody FullNameRequest fullNameRequest) {
        UpdateFullNameResponse response = new UpdateFullNameResponse();

        User user = userRepository.findById(fullNameRequest.getId());

        if (user != null) {
            if (fullNameRequest.getFirstname() != null && !fullNameRequest.getFirstname().isEmpty()) {
                user.setFirstname(fullNameRequest.getFirstname());
            }
            if (fullNameRequest.getLastname() != null && !fullNameRequest.getLastname().isEmpty()) {
                user.setLastname(fullNameRequest.getLastname());
            }

            userRepository.save(user);

            response.setFirstname(user.getFirstname());
            response.setLastname(user.getLastname());


            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<String> addGender(AddGenderRequest addGenderRequest) {
        User user = userRepository.findById(addGenderRequest.getId());
        if (user != null) {
            user.setGender(addGenderRequest.getGender());
            userRepository.save(user);
            String gender = user.getGender();

            return ResponseEntity.ok(gender);
        }
        return ResponseEntity.ok("User not Found");
    }

    @Override
    public ResponseEntity<String> addLocation(AddLocationRequest addLocationRequest) {
        User user = userRepository.findById(addLocationRequest.getId());
        if (user != null) {
            user.setLocation(addLocationRequest.getLocation());
            userRepository.save(user);
            String location = user.getLocation();
            return ResponseEntity.ok(location);

        }
        return ResponseEntity.ok("User not Found");
    }

    @Override
    public ResponseEntity<String> addBirthDate(@RequestBody AddBirthRequest addBirthRequest) {
        User user = userRepository.findById(addBirthRequest.getId());

        if (user != null) {
            String date= addBirthRequest.getBirthday();
            System.out.println(date);
            LocalDate localDate = LocalDate.parse(date);
            user.setDateOfBirth(localDate);
            userRepository.save(user);
            final String dateOfBirth = user.getDateOfBirth().toString();


            return ResponseEntity.ok(dateOfBirth);
        }
        return ResponseEntity.ok("User not Found");
    }

    @Override
    public ResponseEntity<String> addGithub(AddGithubRequest addGithubRequest) {
        User user = userRepository.findById(addGithubRequest.getId());
        if (user != null) {
            user.setGithub(addGithubRequest.getGithub());
            userRepository.save(user);
            String github = user.getGithub();

            return ResponseEntity.ok(github);
        }
        return ResponseEntity.ok("User not Found");
    }

    @Override
    public ResponseEntity<String> addLinkedIn(AddLinkedInRequest addLinkedInRequest) {
        User user = userRepository.findById(addLinkedInRequest.getId());
        if (user != null) {
            user.setLinkedin(addLinkedInRequest.getLinkedin());
            userRepository.save(user);
            String linkedin = user.getLinkedin();

            return ResponseEntity.ok(linkedin);
        }
        return ResponseEntity.ok("User not Found");
    }

    @Override
    public ResponseEntity<String> addWork(AddWorkRequest addWorkRequest) {
        User user = userRepository.findById(addWorkRequest.getId());
        if (user != null) {
            user.setWork(addWorkRequest.getWork());
            userRepository.save(user);
            String userWork = user.getWork();

            return ResponseEntity.ok(userWork);
        }
        return ResponseEntity.ok("User not Found");
    }

    @Override
    public Long getUserCount() {
       return userRepository.findTotalUserCount();
    }


}
