package net.branium.repositories;

import net.branium.domains.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT c FROM Course c ORDER BY c.studyCount DESC, c.buyCount DESC LIMIT 7")
    List<Course> findByStudyCountDescAndBuyCountDesc();
}
