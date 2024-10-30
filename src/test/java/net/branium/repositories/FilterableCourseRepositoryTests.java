package net.branium.repositories;

import net.branium.domains.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"mysql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FilterableCourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepo;

    @Test
    void testListWithDefault() {
        int pageSize = 5;
        int pageNumber = 0;
        String sortField = "title";

        Sort sort = Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> pages = courseRepo.listWithFilter(pageable, Collections.emptyMap());

        List<Course> content = pages.getContent();

        assertEquals(pageSize, content.size());
        Assertions.assertThat(content).isSortedAccordingTo(Comparator.comparing(Course::getTitle));
        content.forEach(System.out::println);
    }
}
