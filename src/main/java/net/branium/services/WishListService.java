package net.branium.services;

import net.branium.dtos.course.CourseResponse;

import java.util.List;

public interface WishListService {
    List<CourseResponse> getCoursesFromUserWishList();
    List<CourseResponse> addCourseToUserWishList(int courseId);
    List<CourseResponse> removeCourseFromUserWishList(int courseId);
}
