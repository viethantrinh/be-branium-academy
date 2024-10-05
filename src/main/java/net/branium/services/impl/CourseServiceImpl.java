package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.Course;
import net.branium.dtos.course.CourseResponse;
import net.branium.mappers.CourseMapper;
import net.branium.repositories.CourseRepository;
import net.branium.services.CourseService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseResponse> getAllPopularCourses() {
        List<Course> courses = courseRepo.findByStudyCountDescAndBuyCountDesc();
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepo.findAll();
        courseRepo.findAll();
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public long getTotalStudentsEnrolledById(int id) {
        long totalStudents = courseRepo.countTotalStudentsEnrolledById(id);
        return totalStudents;
    }
}
