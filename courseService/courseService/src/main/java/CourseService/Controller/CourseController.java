package CourseService.Controller;

import CourseService.Model.Request.AddCourseRequest;
import CourseService.Model.Request.UpdateTopicRequest;
import CourseService.Model.Response.AddCourseResponse;
import CourseService.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestParam("topicName") String topicName,
                                            @RequestParam("description") String description,
                                            @RequestParam("file") MultipartFile file) {
        AddCourseRequest addCourseRequest = new AddCourseRequest();
        addCourseRequest.setTopicName(topicName);
        addCourseRequest.setDescription(description);
        addCourseRequest.setFile(file);

        courseService.addCourse(addCourseRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully added");
    }

    @GetMapping("/get")
    public ResponseEntity<List<AddCourseResponse>> getAllCourseTopic() {
        return courseService.getAllCourserTopic();
    }

    @DeleteMapping("delete/{courseId}")
    public void deleteTopic(@PathVariable Long courseId){
        courseService.deleteCourseTopic(courseId);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestParam("courseId") Long courseId,
                                               @RequestParam("topicName") String topicName,
                                               @RequestParam("description") String description,
                                               @RequestParam("file") MultipartFile file) {
        UpdateTopicRequest updateTopicRequest = new UpdateTopicRequest();
        updateTopicRequest.setCourseId(courseId);
        updateTopicRequest.setTopicName(topicName);
        updateTopicRequest.setDescription(description);
        updateTopicRequest.setFile(file);

        courseService.updateTopic(updateTopicRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
    }

}
