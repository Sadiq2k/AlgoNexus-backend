package CourseService.Reposistory;

import CourseService.Entities.Video;
import CourseService.Model.Response.VideoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {

    @Query("SELECT new CourseService.Model.Response.VideoResponse(v.id, v.title, v.description, v.videoName, v.videoUrl, v.videoId) " +
            "FROM Video v WHERE v.course.courseId = :courseId")
    List<VideoResponse> findByCourse_CourseId(@Param("courseId")Long courseId);


}
