package net.branium.services;

import net.branium.dtos.course.CourseResponse;

import java.util.List;

public interface CartService {
    long getCartQuantities(String userId);
    List<CourseResponse> getCoursesFromUserCart();
    List<CourseResponse> addCourseToUserCart(int courseId);
    List<CourseResponse> removeCourseFromUserCart(int courseId);
}
