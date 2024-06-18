package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.course.response.CourseBA;
import net.branium.dtos.course.response.CourseItemResponse;
import net.branium.mappers.CourseMapper;
import net.branium.services.CourseService;
import net.branium.utils.Constants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;


    @GetMapping("/courses")
    public Flux<CourseItemResponse> getCourseItems() {
        return courseService.getCourseItems();
    }


}
