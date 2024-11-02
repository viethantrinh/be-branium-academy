package net.branium.controllers;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import net.branium.domains.Category;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.course.CourseDetailResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.CartService;
import net.branium.services.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/courses")
@RequiredArgsConstructor
@Validated
public class CourseController {

    private final CourseService courseService;
    private Map<String, String> sortProperty = Map.of(
            "", "",
            "priceAsc", "priceAsc",
            "priceDesc", "priceDesc",
            "popular", "popular"
    );

    private Map<String, Object> category = Map.of(
            "", "",
            "java", Category.builder().id(1).title("Java").build(),
            "c++", Category.builder().id(2).title("C++").build(),
            "c#", Category.builder().id(3).title("C#").build(),
            "c", Category.builder().id(4).title("C").build(),
            "python", Category.builder().id(5).title("Python").build(),
            "android", Category.builder().id(6).title("Android").build(),
            "git", Category.builder().id(7).title("Git").build(),
            "flutter", Category.builder().id(8).title("Flutter").build(),
            "sql", Category.builder().id(9).title("SQL").build()
    );

    @GetMapping(path = "/search")
    public ResponseEntity<ApiResponse<?>> searchForCourses(
            @RequestParam(name = "page", required = false, defaultValue = "1")
            @Min(value = 1)
            Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5")
            @Min(value = 5)
            @Max(value = 10)
            Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "")
            String sort,
            @RequestParam(name = "category", required = false, defaultValue = "")
            String category,
            @RequestParam(name = "keyword", required = true)
            @Pattern.List({
//                    @Pattern(regexp = "^[^0-9]*$", message = "Keyword must not have number"),
                    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Keyword must not have special characters")
            })
            String keyword
    ) {
        if (!sortProperty.containsKey(sort)) {
            throw new ApplicationException(ErrorCode.INVALID_PARAM);
        }


        if (!this.category.containsKey(category)) {
            throw new ApplicationException(ErrorCode.INVALID_PARAM);
        }

        Map<String, Object> filterFields = new HashMap<>();

        if (!"".equals(category)) {
            filterFields.put("category", this.category.get(category));
        }

        if (!"".equals(keyword)) {
            filterFields.put("title", keyword);
        }

        var courses = courseService.getAllCourses(page - 1, size, sort, filterFields);
        var response = ApiResponse.<CollectionModel<CourseResponse>>builder()
                .message("search courses success")
                .result(courses)
                .build();
        return courses.getContent().isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }


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

    @PostMapping(path = "/{id}/enroll")
    public ResponseEntity<ApiResponse<?>> enrollInCourse(@PathVariable(name = "id") String id) {
        Map<String, Boolean> result = new HashMap<>();
        boolean isSucceeded = courseService.enrollInCourse(Integer.parseInt(id));
        result.put("result", isSucceeded);
        var response = ApiResponse.<Object>builder()
                .message(isSucceeded ? "enroll in course successful" : "enroll in course unsuccessful")
                .result(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{id}/go-to-course")
    public ResponseEntity<ApiResponse<?>> increaseStudyCount(@PathVariable(name = "id") String id) {
        Map<String, Integer> results = new HashMap<>();
        int studyCount = courseService.increaseStudyCount(Integer.parseInt(id));
        results.put("studyCount", studyCount);
        var response = ApiResponse.<Object>builder()
                .message("Increase study count of course success")
                .result(results)
                .build();
        return ResponseEntity.ok(response);
    }

}
