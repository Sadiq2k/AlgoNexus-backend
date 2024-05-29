package CourseService.Controller;

import CourseService.Entities.Video;
import CourseService.Model.Response.VideoResponse;
import CourseService.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/add")
    public ResponseEntity<Video> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("courseId")Long courseId) {
        try {
            Video video = videoService.uploadVideo(file, title, description,courseId);
            return ResponseEntity.ok(video);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/video/{id}")
    public void deleteVideo(@PathVariable Long id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is missing");
        }
        videoService.deleteVideo(id);
    }

    @PostMapping("/update")
    public ResponseEntity<Video> updateVideo(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("videoId") Long videoId) {
        try {
            Video video = videoService.updateVideo(file, title, description,videoId);
            return ResponseEntity.ok(video);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/get-videos")
    public List<VideoResponse> getVideos(@RequestParam("courseId") Long courseId){
        return videoService.getVideos(courseId);
    }

}
