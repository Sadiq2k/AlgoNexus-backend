package CourseService.Service.Impl;

import CourseService.Service.CloudinaryImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Map uploadVideo(MultipartFile file) {
        try {
            // Add resource_type as 'video' to ensure it's treated as a video
            return cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "video"));
        } catch (IOException e) {
            throw new RuntimeException("Video uploading failed!", e);
        }
    }

    public void deleteVideo(String videoId) {
        try {
            // Delete the video from Cloudinary
            cloudinary.uploader().destroy(videoId, ObjectUtils.emptyMap());
            System.out.println("Deletion successful");
        } catch (IOException e) {
            // Handle IOException
            throw new RuntimeException("Failed to delete video from Cloudinary", e);
        }
    }




}
