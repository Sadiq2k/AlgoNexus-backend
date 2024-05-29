package CourseService.Reposistory;

import CourseService.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    boolean existsByTopicName(String topicName);
}
