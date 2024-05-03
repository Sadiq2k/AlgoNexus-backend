package com.algo.nexus.userService.Serivce.Impl;

import com.algo.nexus.userService.Entities.UserProfileImage;
import com.algo.nexus.userService.Repository.UserProfileImageRepository;
import com.algo.nexus.userService.Serivce.CloudinaryImageService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserProfileImageRepository userProfileImageRepository;

    @Override
    public Map upload(MultipartFile file){

        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        } catch (IOException e) {
            throw new RuntimeException("image uploading failed !!");
        }


    }


}
