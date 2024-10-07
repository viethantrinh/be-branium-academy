package net.branium.repositories;

import net.branium.domains.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT c FROM Course c ORDER BY c.studyCount DESC, c.buyCount DESC LIMIT 7")
    List<Course> findAllByStudyCountDescAndBuyCountDesc();

    /**
     * Count total students which enrolled in a course
     * @param courseId the id of course want to count
     * @return number of student which enrolled in the course
     */
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = ?1")
    long countTotalStudentsEnrolledById(Integer courseId);

    /**
     * Find all courses which owned (enrolled buy user)
     * @param userId the id of user which want to get data
     * @return all the courses which owned by user
     */
    List<Course> findAllByEnrollmentsUserId(String userId);


    /**
     * Find all the Courses which belong to the specific Order by its id
     * @param id the id of the order
     * @return All the Courses which belong to the specific Order
     */
    List<Course> findAllByOrderDetailsOrderId(Integer id);
}
