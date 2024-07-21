package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.course.response.CourseItemResponse;
import net.branium.mappers.CourseMapper;
import net.branium.services.impl.CourseServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(value = {"*"})
@RequiredArgsConstructor
public class CourseController {
    private final CourseServiceImpl courseServiceImpl;

    @GetMapping("/courses")
    public Flux<CourseItemResponse> getCourseItems() {
        return courseServiceImpl.getCourseItems();
    }


}
