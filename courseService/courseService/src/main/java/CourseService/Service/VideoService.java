package CourseService.Service;

import CourseService.Entities.Video;
import CourseService.Model.Response.VideoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    Video uploadVideo(MultipartFile file, String title, String description,Long courseId) throws IOException;

    void deleteVideo(Long id) throws IOException;

    Video updateVideo(MultipartFile file, String title, String description,Long videoId)throws IOException;

    List<VideoResponse> getVideos(Long courseId);
}
