package CourseService.Service.Impl;

import CourseService.Entities.Course;
import CourseService.Entities.Video;
import CourseService.Model.Response.VideoResponse;
import CourseService.Reposistory.CourseRepository;
import CourseService.Reposistory.VideoRepository;
import CourseService.Service.CourseService;
import CourseService.Service.VideoService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseService courseService;

    @Autowired
    private CloudinaryImageServiceImpl cloudinaryImageService;

    @Override
    public Video uploadVideo(MultipartFile file, String title, String description,Long courseId) throws IOException {
        Map uploadResult = cloudinaryImageService.uploadVideo(file);
        Course course = courseRepository.findById(courseId).get();

        Video video = Video.builder()
                .title(title)
                .course(course)
                .description(description)
                .videoUrl(uploadResult.get("url").toString())
                .videoName(file.getOriginalFilename())
                .videoId(uploadResult.get("public_id").toString())
                .build();

        return videoRepository.save(video);
    }

    @Override
    public void deleteVideo(Long id) throws IOException {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()){
            cloudinaryImageService.delete(video.get().getVideoId());
        }
        videoRepository.deleteById(id);
    }

    @Override
    public Video updateVideo(MultipartFile file, String title, String description, Long videoId) throws IOException {
        Video existingVideo = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        if (file != null) {
            cloudinaryImageService.deleteVideo(existingVideo.getVideoId());
            Map uploadResult = cloudinaryImageService.uploadVideo(file);
            existingVideo.setVideoUrl(uploadResult.get("url").toString());
            existingVideo.setVideoName(file.getOriginalFilename());
            existingVideo.setVideoId(uploadResult.get("public_id").toString());
        }

        if (title != null) {
            existingVideo.setTitle(title);
        }

        if (description != null) {
            existingVideo.setDescription(description);
        }

        return videoRepository.save(existingVideo);
    }

    @Override
    public List<VideoResponse> getVideos(Long courseId) {
      return videoRepository.findByCourse_CourseId(courseId);

    }
}
