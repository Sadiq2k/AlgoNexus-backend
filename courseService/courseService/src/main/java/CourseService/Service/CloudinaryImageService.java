package CourseService.Service;

import CourseService.Model.Response.AddCourseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CloudinaryImageService {

    public Map upload(MultipartFile file);


    void delete(String imageId) throws IOException;

}
