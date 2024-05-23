package CourseService.Service;

import CourseService.Entities.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService {
    Video uploadVideo(MultipartFile file, String title, String description) throws IOException;
}
