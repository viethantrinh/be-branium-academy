package net.branium.controllers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/carts")
@RequiredArgsConstructor
@Validated
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
    public ResponseEntity<ApiResponse<List<CourseResponse>>> addNewCourseInCartForUser(
            @PathVariable(name = "id")
            String id
    ) {
        if (id.isEmpty() || id.isBlank() || id.equals("null")) {
            throw new ApplicationException(ErrorCode.INVALID_PARAM);
        }

        int courseId;
        try {
            courseId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ApplicationException(ErrorCode.BAD_REQUEST);
        }
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
