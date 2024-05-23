package CourseService.Service.Impl;

import CourseService.Model.Response.AddCourseResponse;
import CourseService.Service.CloudinaryImageService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file){

        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        } catch (IOException e) {
            throw new RuntimeException("image uploading failed !!");
        }
    }


    @Override
    public void delete(String imageId) throws IOException {
        Map result = cloudinary.uploader().destroy(imageId, Map.of());
        if ("ok".equals(result.get("result"))) {
            System.out.println("Deletion successful");
        } else {
            System.out.println("Deletion failure");
        }
    }


}
