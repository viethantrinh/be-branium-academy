package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.course.CourseDetailResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.services.CartService;
import net.branium.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> listAllCoursesWhichBoughtByUser() {
        List<CourseResponse> courses = courseService.getAllCoursesBoughtByUser();
        var response = ApiResponse.<List<CourseResponse>>builder()
                .message("get all the courses which bought by user success")
                .result(courses)
                .build();
        return courses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<CourseDetailResponse>> getCourseDetails(@PathVariable(name = "id") String id) {
        int courseId = Integer.parseInt(id);
        CourseDetailResponse courseDetails = courseService.getCourseDetails(courseId);
        var response = ApiResponse.<CourseDetailResponse>builder()
                .message("get course information by id success")
                .result(courseDetails)
                .build();
        return ResponseEntity.ok(response);
    }

}
