package net.branium.services;

import net.branium.dtos.course.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllPopularCourses();
    List<CourseResponse> getAllCourses();
    long getTotalStudentsEnrolledById(int id);
}
