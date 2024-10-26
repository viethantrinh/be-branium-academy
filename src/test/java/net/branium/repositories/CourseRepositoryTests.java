package net.branium.repositories;

import net.branium.domains.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"mysql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepo;
    private Course course;

    @BeforeEach
    void setUp() {
        course = Course.builder()
                .id(1)
                .title("Java in action")
                .shortDescription("blah blah blah")
                .fullDescription("blah blah blah")
                .price(BigDecimal.valueOf(3_000_000L))
                .discountPrice(BigDecimal.valueOf(2_500_000L))
                .buyCount(0)
                .studyCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
    }

    @Test
    @DisplayName("test get course by id success")
    void givenCourseObject_whenSave_thenReturnCourseObject() {
        Course savedCourse = courseRepo.save(course);
        assertNotNull(savedCourse);
    }

    @Test
    void testListFirstPage() {
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> pages = courseRepo.findAllWithOption(pageable);
        assertEquals(size, pages.getSize());
        pages.forEach(System.out::println);
    }

    @Test
    void testListSecondPageWithSort() {
        int page = 1;
        int size = 5;
        Sort sort = Sort.by("price").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Course> pages = courseRepo.findAllWithOption(pageable);
        System.out.println(pages.getTotalElements());
        System.out.println(pages.getTotalPages());
        pages.forEach(System.out::println);
    }
}
