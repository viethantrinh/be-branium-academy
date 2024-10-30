package net.branium.repositories;

import net.branium.domains.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FilterableCourseRepository {
    Page<Course> listWithFilter(Pageable pageable, Map<String, Object> filterFields);
}
