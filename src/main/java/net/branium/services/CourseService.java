package net.branium.services;

import jakarta.validation.Valid;
import net.branium.dtos.course.CourseCreateRequest;
import net.branium.dtos.course.CourseResponse;

public interface CourseService {

    CourseResponse createCourse(CourseCreateRequest request);
}
