package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping(path = "/items")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> listAllCoursesInCartForUser() {
        List<CourseResponse> courseResponses = cartService.getCoursesFromUserCart();
        var response = ApiResponse.<List<CourseResponse>>builder()
                .message("get user's courses in cart success")
                .result(courseResponses)
                .build();
        return courseResponses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @PostMapping(path = "/items/{id}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> addNewCourseInCartForUser(@PathVariable(name = "id") String id) {
        int courseId = Integer.parseInt(id);
        List<CourseResponse> courseResponses = cartService.addCourseToUserCart(courseId);
        var response = ApiResponse.<List<CourseResponse>>builder()
                .message("add course to user's cart success")
                .result(courseResponses)
                .build();
        return courseResponses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> deleteCourseInCartForUser(@PathVariable(name = "id") String id) {
        int courseId = Integer.parseInt(id);
        List<CourseResponse> courseResponses = cartService.removeCourseFromUserCart(courseId);
        var response = ApiResponse.<List<CourseResponse>>builder()
                .message("remove course from user's cart success")
                .result(courseResponses)
                .build();
        return courseResponses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }
}
