package net.branium.repositories;

import net.branium.domains.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    /**
     * Check if User with id is enrolled in Course with id
     * @param userId user's id want to check
     * @param courseId course's id want to check
     * @return true if user is enrolled, otherwise return false
     */
    @Query("select (count(e) > 0) from Enrollment e where e.user.id = ?1 and e.course.id = ?2")
    boolean isUserEnrolled(String userId, int courseId);


}
