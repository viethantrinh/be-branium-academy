package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.Course;
import net.branium.domains.Order;
import net.branium.domains.OrderStatus;
import net.branium.dtos.course.CourseDetailResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.mappers.CourseMapper;
import net.branium.repositories.CourseRepository;
import net.branium.repositories.OrderRepository;
import net.branium.services.CourseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final OrderRepository orderRepo;
    private final CourseMapper courseMapper;


    @Override
    public List<CourseResponse> getAllPopularCourses() {
        List<Course> courses = courseRepo.findAllByStudyCountDescAndBuyCountDesc();
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepo.findAll();
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public long getTotalStudentsEnrolled(int id) {
        long totalStudents = courseRepo.countTotalStudentsEnrolledById(id);
        return totalStudents;
    }

    @Override
    public List<CourseResponse> getAllCoursesEnrolledByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Course> courses = courseRepo.findAllByEnrollmentsUserId(authentication.getName());
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public List<CourseResponse> getAllCoursesBoughtByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Order> orders = orderRepo.findAllByUserIdAndOrderStatus(authentication.getName(), OrderStatus.SUCCEEDED);
        List<Course> courses = new ArrayList<>();
        for (Order order : orders) {
            List<Course> boughtCourses = courseRepo.findAllByOrderDetailsOrderId(order.getId());
            courses.addAll(boughtCourses);
        }
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public CourseDetailResponse getCourseDetails(int id) {
        return null;
    }
}
