package net.branium.services;

import net.branium.dtos.category.CategoryResponse;
import net.branium.dtos.course.CourseDetailResponse;
import net.branium.dtos.course.CourseResponse;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

public interface CourseService {
    CollectionModel<CourseResponse> getAllCourses(int page, int size, String sort);
    List<CourseResponse> getAllPopularCourses();
    long getTotalStudentsEnrolled(int courseId);
    List<CourseResponse> getAllCoursesBoughtByUser();
    CourseDetailResponse getCourseDetails(int courseId);
    List<CourseResponse> getAllCoursesByCategoryId(int categoryId);
    boolean enrollInCourse(int courseId);
    int increaseStudyCount(int courseId);
    int increaseBuyCount(int courseId);
}
