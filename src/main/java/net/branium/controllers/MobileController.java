package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.category.CategoryResponse;
import net.branium.dtos.course.CourseHomeResponse;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        long cartQuantities = cartService.getCartQuantities(authentication.getName());
        List<CourseHomeResponse> popularCourses = courseService.getAllPopularCourses()
                .stream()
                .map((c) -> CourseHomeResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .image(c.getImage())
                        .price(c.getPrice())
                        .discountPrice(c.getDiscountPrice())
                        .totalStudents((int) courseService.getTotalStudentsEnrolled(c.getId()))
                        .build())
                .limit(7)
                .collect(Collectors.toList());
        List<CategoryResponse> categories = categoryService.getAllCategories();
        List<CourseResponse> topPickCourses = courseService.getAllPopularCourses()
                .stream()
                .limit(5)
                .toList();

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("cartQuantities", cartQuantities);
        responseBody.put("popularCourses", popularCourses);
        responseBody.put("categories", categories);
        responseBody.put("topPicks", topPickCourses);
        return responseBody;
    }


}
