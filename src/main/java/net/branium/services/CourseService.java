package net.branium.services;

import net.branium.dtos.course.CourseCreateRequest;
import net.branium.dtos.course.CourseDetailResponse;
import net.branium.dtos.course.CourseResponse;
import org.springframework.hateoas.CollectionModel;

import java.util.List;
import java.util.Map;

public interface CourseService {
    CollectionModel<CourseResponse> getAllCourses(int page, int size, String sort, Map<String, Object> filterFields);
    List<CourseResponse> getAllPopularCourses();
    long getTotalStudentsEnrolled(int courseId);
    List<CourseResponse> getAllCoursesBoughtByUser();
    CourseDetailResponse getCourseDetails(int courseId);
    List<CourseResponse> getAllCoursesByCategoryId(int categoryId);
    boolean enrollInCourse(int courseId);
    int increaseStudyCount(int courseId);
    int increaseBuyCount(int courseId);
    CourseResponse createCourse(CourseCreateRequest request);
}
