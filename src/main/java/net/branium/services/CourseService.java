package net.branium.services;

import net.branium.dtos.category.CategoryResponse;
import net.branium.dtos.course.CourseDetailResponse;
import net.branium.dtos.course.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllPopularCourses();
    List<CourseResponse> getAllCourses();
    long getTotalStudentsEnrolled(int courseId);
    List<CourseResponse> getAllCoursesBoughtByUser();
    CourseDetailResponse getCourseDetails(int courseId);
    List<CourseResponse> getAllCoursesByCategoryId(int categoryId);
    boolean enrollInCourse(int courseId);
    int increaseStudyCount(int courseId);
}
