package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.category.CategoryResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.services.CategoryService;
import net.branium.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> listAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
        var response = ApiResponse.<List<CategoryResponse>>builder()
                .message("get all categories success")
                .result(categoryResponses)
                .build();
        return categoryResponses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}/courses")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> listAllCoursesByCategory(@PathVariable(name = "id") String id) {
        int categoryId = Integer.parseInt(id);
        List<CourseResponse> courseResponses = courseService.getAllCoursesByCategoryId(categoryId);
        var response = ApiResponse.<List<CourseResponse>>builder()
                .message("get all the courses of category success")
                .result(courseResponses)
                .build();
        return courseResponses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

}
