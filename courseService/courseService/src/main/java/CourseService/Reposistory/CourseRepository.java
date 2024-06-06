package CourseService.Reposistory;

import CourseService.Entities.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    boolean existsByTopicName(String topicName);

    @Query(value = "SELECT COUNT(*) FROM course", nativeQuery = true)
    Long findTotalCourse();


}
