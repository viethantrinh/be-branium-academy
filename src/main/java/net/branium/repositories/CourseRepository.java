package net.branium.repositories;

import net.branium.domains.Course;
import net.branium.domains.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT c FROM Course c ORDER BY c.studyCount DESC, c.buyCount DESC LIMIT 7")
    List<Course> findByStudyCountDescAndBuyCountDesc();

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
    List<Course> findByOrderDetailsOrderId(int courseId);


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
    List<Lecture> findLecturesByCourseId(int courseId);

    @Query("SELECT c FROM Course  c WHERE c.category.id = ?1")
    List<Course> findByCategoryId(int categoryId);
}
