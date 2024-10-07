package net.branium.services;

import net.branium.dtos.course.CourseDetailResponse;
import net.branium.dtos.course.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllPopularCourses();
    List<CourseResponse> getAllCourses();
    long getTotalStudentsEnrolled(int id);
    List<CourseResponse> getAllCoursesEnrolledByUser();
    List<CourseResponse> getAllCoursesBoughtByUser();
    CourseDetailResponse getCourseDetails(int id);

}
