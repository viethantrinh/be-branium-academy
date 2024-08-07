package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.course.CourseBA;
import net.branium.dtos.course.CourseItemResponse;
import net.branium.mappers.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl {
    private final WebClient webClient;
    private final CourseMapper courseMapper;

    public Flux<CourseItemResponse> getCourseItems() {
        String uri = "/wp-json/learnpress/v1/courses";
        Flux<CourseItemResponse> courseItemResponseFlux = webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(CourseBA.class)
                .map(courseBA -> courseMapper.toCourseItemResponse(courseBA));
        return courseItemResponseFlux;
    }
}
