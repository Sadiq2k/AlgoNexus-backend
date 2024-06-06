package CourseService.Service.Impl;

import CourseService.Entities.Course;
import CourseService.Entities.Video;
import CourseService.Model.Exception.TopicNameAlreadyExistsException;
import CourseService.Model.Request.AddCourseRequest;
import CourseService.Model.Request.UpdateTopicRequest;
import CourseService.Model.Response.AddCourseResponse;
import CourseService.Model.Response.VideoResponse;
import CourseService.Reposistory.CourseRepository;
import CourseService.Reposistory.VideoRepository;
import CourseService.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CloudinaryImageServiceImpl cloudinaryImageService;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private VideoRepository videoRepository;
    @Override
    public void addCourse(AddCourseRequest request) {

        if (courseRepository.existsByTopicName(request.getTopicName())) {
            throw new TopicNameAlreadyExistsException("Topic name already exists");
        }

        Map upload = cloudinaryImageService.upload(request.getFile());

        Course course = Course.builder()
                .topicName(request.getTopicName())
                .description(request.getDescription())
                .imageUrl(upload.get("url").toString())
                .imageName(request.getFile().getOriginalFilename())
                .imageId(upload.get("public_id").toString())
                .build();

        courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<AddCourseResponse>> getAllCourserTopic(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page,size);

        Page<Course> pageCourse = courseRepository.findAll(pageable);
         List<Course> allTopics = pageCourse.getContent();


        List<AddCourseResponse> responseList = new ArrayList<>();
        for (Course course : allTopics) {
            List<VideoResponse> getVideos = getVideos(course.getCourseId());
            final AddCourseResponse response = getAddCourseResponse(course, getVideos);
            responseList.add(response);

        }
        Page<AddCourseResponse> responsePage = new PageImpl<>(responseList, pageable, pageCourse.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(responsePage);
    }

    private static AddCourseResponse getAddCourseResponse(Course course, List<VideoResponse> getVideos) {
        int totalVideoCount = getVideos.size();

        AddCourseResponse response = new AddCourseResponse();
        response.setTotalVideoCount(totalVideoCount);
        response.setCourses(course);
            return response;
    }

    public List<VideoResponse> getVideos(Long courseId) {
        return videoRepository.findByCourse_CourseId(courseId);

    }

    @Override
    public void deleteCourseTopic(Long courseId) throws IOException {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()){
            cloudinaryImageService.delete(course.get().getImageId());

            for (Video video : course.get().getVideos()) {
                cloudinaryImageService.deleteVideo(video.getVideoId());
            }
        }

        courseRepository.deleteById(courseId);
    }

    @Override
    public Course updateTopic(UpdateTopicRequest request) throws IOException {
        Course existingVideo = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("topic not found"));

        if (request.getFile() != null) {
            cloudinaryImageService.delete(existingVideo.getImageId());
            Map uploadResult = cloudinaryImageService.upload(request.getFile());
            existingVideo.setImageUrl(uploadResult.get("url").toString());
            existingVideo.setImageName(request.getFile().getOriginalFilename());
            existingVideo.setImageId(uploadResult.get("public_id").toString());
        }


        if (request.getTopicName() != null) {
            existingVideo.setTopicName(request.getTopicName());
        }

        if (request.getDescription() != null) {
            existingVideo.setDescription(request.getDescription());
        }

        return courseRepository.save(existingVideo);
        }

    @Override
    public Long getTotalCourse() {
        return courseRepository.findTotalCourse();
    }

    @Override
    public List<AddCourseResponse> getLatestCourseTopic() {

        List<Course> allCourses = courseRepository.findAll();

        // Sort the courses by courseId in descending order
        List<Course> sortedCourses = allCourses.stream()
                .sorted(Comparator.comparingLong(Course::getCourseId).reversed())
                .collect(Collectors.toList());

        // Select the top two courses
        List<Course> latestCourses = sortedCourses.stream().limit(2).collect(Collectors.toList());

        // Convert Course objects to AddCourseResponse objects
        List<AddCourseResponse> responseList = new ArrayList<>();
        for (Course course : latestCourses) {
            List<VideoResponse> videos = getVideos(course.getCourseId());
            AddCourseResponse response = getAddCourseResponse(course, videos);
            responseList.add(response);
        }
        return responseList;
    }




}
