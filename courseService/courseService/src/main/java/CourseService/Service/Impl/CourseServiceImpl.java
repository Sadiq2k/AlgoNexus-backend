package CourseService.Service.Impl;

import CourseService.Entities.Course;
import CourseService.Model.Exception.ResourceNotFoundException;
import CourseService.Model.Request.AddCourseRequest;
import CourseService.Model.Request.UpdateTopicRequest;
import CourseService.Model.Response.AddCourseResponse;
import CourseService.Reposistory.CourseRepository;
import CourseService.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CloudinaryImageServiceImpl cloudinaryImageService;
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public void addCourse(AddCourseRequest addCourseRequest) {
        Map upload = cloudinaryImageService.upload(addCourseRequest.getFile());

        Course course = Course.builder()
                .topicName(addCourseRequest.getTopicName())
                .description(addCourseRequest.getDescription())
                .imageUrl(upload.get("url").toString())
                .imageName(addCourseRequest.getFile().getOriginalFilename())
                .imageId(upload.get("public_id").toString())
                .build();

        courseRepository.save(course);
    }

    @Override
    public ResponseEntity<List<AddCourseResponse>> getAllCourserTopic() {
        List<Course> allTopics = courseRepository.findAll();
        List<AddCourseResponse> responseList = new ArrayList<>();
        for (Course course : allTopics) {
            AddCourseResponse response = new AddCourseResponse();
            response.setTopicName(course.getTopicName());
            response.setDescription(course.getDescription());
            response.setImageUrl(course.getImageUrl());
            response.setImageName(course.getImageName());
            response.setImageId(course.getImageId());
            response.setCourseId(course.getCourseId());
            responseList.add(response);

        }
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @Override
    public void deleteCourseTopic(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public void updateTopic(UpdateTopicRequest request) {
        Map<String, String> upload = cloudinaryImageService.upload(request.getFile());

        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.setTopicName(request.getTopicName());
            course.setDescription(request.getDescription());
            course.setImageUrl(upload.get("url"));
            course.setImageId(upload.get("public_id"));
            course.setImageName(request.getFile().getOriginalFilename());

            courseRepository.save(course);
        } else {
            throw new ResourceNotFoundException("Course with ID " + request.getCourseId() + " not found.");
        }
    }


}
