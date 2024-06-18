package net.branium.services;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.course.response.CourseBA;
import net.branium.dtos.course.response.CourseItemResponse;
import net.branium.mappers.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class CourseService {
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

    public Flux<CourseBA> getCourseBAs() {
        String uri = "/wp-json/learnpress/v1/courses";
        return webClient.get().uri(uri).retrieve().bodyToFlux(CourseBA.class);
    }
}
