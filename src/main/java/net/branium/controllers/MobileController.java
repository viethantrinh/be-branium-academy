package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.category.CategoryResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.services.CartService;
import net.branium.services.CategoryService;
import net.branium.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(path = "/mobile")
@RequiredArgsConstructor
public class MobileController {

    private final CourseService courseService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping(path = "/home-page")
    public ResponseEntity<ApiResponse<?>> getHomePageInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> responseBody = buildResponseBody(authentication);
        var response = ApiResponse.<Object>builder()
                .message("get mobile's home-page information success")
                .result(responseBody)
                .build();
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> buildResponseBody(Authentication authentication) {
        long cartQuantities = cartService.getCartQuantitiesByUserId(authentication.getName());
        List<CourseResponse> popularCourses = courseService.getAllPopularCourses();
        List<CategoryResponse> categories = categoryService.getAllCategories();
        List<CourseResponse> topPickCourses = courseService.getAllCourses();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("cartQuantities", cartQuantities);
        responseBody.put("popularCourses", popularCourses);
        responseBody.put("categories", categories);
        responseBody.put("topPicks", topPickCourses);
        return responseBody;
    }


}
