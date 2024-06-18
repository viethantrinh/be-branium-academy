package net.branium;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.branium.dtos.course.response.CourseBA;
import net.branium.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class WebClientTest {
    WebClient webClient;
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        webClient = WebClient.create(Constants.BA_WP_BASE_URL);
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGetOneCourseBADataWebClient() {
        String uri = "/wp-json/learnpress/v1/courses/57475";
        Mono<CourseBA> courseBAResponseMono = webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CourseBA.class);

        StepVerifier.create(courseBAResponseMono)
                .consumeNextWith(System.out::println)
                .verifyComplete();
    }

    @Test
    public void testGetOneCourseBADataRestTemplate() throws JsonProcessingException {
        String uri = "/wp-json/learnpress/v1/courses/57475";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        CourseBA courseBA = restTemplate.getForObject(Constants.BA_WP_BASE_URL + uri, CourseBA.class);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(courseBA));
    }

    @Test
    public void testGetListCourseBAWebClient() {
        String uri = "/wp-json/learnpress/v1/courses";
        Flux<CourseBA> courseBAFlux = webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(CourseBA.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        StepVerifier.create(courseBAFlux)
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .consumeNextWith(value -> {
                    try {
                        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .verifyComplete();
    }

    @Test
    public void testGetListCourseBARestTemplate() throws JsonProcessingException {
        ParameterizedTypeReference<List<CourseBA>> typeReference = new ParameterizedTypeReference<List<CourseBA>>() {};
        String uri = "/wp-json/learnpress/v1/courses";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<CourseBA> courseBA = restTemplate.exchange(Constants.BA_WP_BASE_URL + uri, HttpMethod.GET, null, typeReference).getBody();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(courseBA));
    }
}
