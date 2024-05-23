package CourseService.Service.Impl;

import CourseService.Entities.Video;
import CourseService.Reposistory.VideoRepository;
import CourseService.Service.VideoService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CloudinaryImageServiceImpl cloudinaryImageService;

    public Video uploadVideo(MultipartFile file, String title, String description) throws IOException {
        Map uploadResult = cloudinaryImageService.upload(file);

        Video video = Video.builder()
                .title(title)
                .description(description)
                .videoUrl(uploadResult.get("url").toString())
                .videoName(file.getOriginalFilename())
                .videoId(uploadResult.get("public_id").toString())
                .build();

        return videoRepository.save(video);
    }
}
