package CourseService.Controller;

import CourseService.Entities.Course;
import CourseService.Model.Exception.TopicNameAlreadyExistsException;
import CourseService.Model.Request.AddCourseRequest;
import CourseService.Model.Request.UpdateTopicRequest;
import CourseService.Model.Response.AddCourseResponse;
import CourseService.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        try {
            courseService.addCourse(addCourseRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully added");
        } catch (TopicNameAlreadyExistsException ex) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Page<AddCourseResponse>> getAllCourseTopic(@RequestParam(value = "page",defaultValue = "0",required = false) Integer page,
                                                                     @RequestParam(value = "size",defaultValue = "5",required = false) Integer size) {
        return courseService.getAllCourserTopic(page,size);
    }

    @GetMapping("/latest")
    public List<AddCourseResponse> getLatestCourseTopic(){
      return  courseService.getLatestCourseTopic();
    }

    @DeleteMapping("delete/{courseId}")
    public void deleteTopic(@PathVariable Long courseId) throws IOException {
        courseService.deleteCourseTopic(courseId);
    }

    @PostMapping("/update")
    public ResponseEntity<Course> updateCourse(@RequestParam("courseId") Long courseId,
                                               @RequestParam(value = "topicName",required = false) String topicName,
                                               @RequestParam(value = "description",required = false) String description,
                                               @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        UpdateTopicRequest updateTopicRequest = new UpdateTopicRequest();
        updateTopicRequest.setCourseId(courseId);
        updateTopicRequest.setTopicName(topicName);
        updateTopicRequest.setDescription(description);
        updateTopicRequest.setFile(file);

        final Course course = courseService.updateTopic(updateTopicRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(course);
    }

    @GetMapping("/getTotalCourse")
    public Long getTotalCourse(){
        return courseService.getTotalCourse();
    }




}
