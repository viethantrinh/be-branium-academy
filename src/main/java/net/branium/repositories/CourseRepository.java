package net.branium.repositories;

import net.branium.domains.Course;
import net.branium.domains.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT c FROM Course c ORDER BY c.studyCount DESC, c.buyCount DESC LIMIT 7")
    List<Course> findAllByStudyCountDescAndBuyCountDesc();

    /**
     * Count total students which enrolled in a course
     *
     * @param courseId the id of course want to count
     * @return number of student which enrolled in the course
     */
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = ?1")
    long countTotalStudentsEnrolledById(int courseId);

    /**
     * Find all the Courses which belong to the specific Order by its id
     *
     * @param courseId the id of the order
     * @return All the Courses which belong to the specific Order
     */
    List<Course> findAllByOrderDetailsOrderId(int courseId);

    /**
     * Check if User with id is enrolled in Course with id
     * @param userId user's id want to check
     * @param courseId course's id want to check
     * @return true if user is enrolled, otherwise return false
     */
    @Query(value = """
            SELECT 
                (COUNT(*) > 0)
            FROM    
                `ENROLLMENT` e
            WHERE 
                e.user_id = ?1
                AND
                e.course_id = ?2
            """, nativeQuery = true)
    long isUserEnrolled(String userId, int courseId);

    @Query(value = """
            SELECT 
                COUNT(*)
            FROM    
                `COURSE` e
                INNER JOIN `SECTION` s ON e.id = s.course_id
            WHERE 
                e.id = ?1
            """, nativeQuery = true)
    long countTotalSectionsByCourseId(int courseId);

    @Query(value = """
            SELECT 
                COUNT(*)
            FROM    
                `COURSE` e
                INNER JOIN `SECTION` s ON e.id = s.course_id
                INNER JOIN `LECTURE` l ON s.id = l.section_id
            WHERE 
                e.id = ?1
            """, nativeQuery = true)
    long countTotalLecturesByCourseId(int courseId);

    @Query("SELECT l FROM Lecture l WHERE l.section.course.id = ?1")
    List<Lecture> findAllLecturesByCourseId(int courseId);

//    @Query("""
//            SELECT c
//            FROM Course c
//            INNER JOIN Section s ON c.id = s.course.id
//            INNER JOIN Lecture l ON s.id = l.section.id
//            WHERE c.id = ?1
//            """)
//    Optional<Course> findCourseDetailsById(int courseId);
}
