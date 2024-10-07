package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.services.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/wish-lists")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @GetMapping(path = "/items")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> listAllCoursesInWishListForUser() {
        List<CourseResponse> courseResponses = wishListService.getCoursesFromUserWishList();
        var response = ApiResponse.<List<CourseResponse>>builder()
                .message("get user's courses in wish list success")
                .result(courseResponses)
                .build();
        return courseResponses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @PostMapping(path = "/items/{id}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> addNewCourseInCartForUser(@PathVariable(name = "id") String id) {
        int courseId = Integer.parseInt(id);
        List<CourseResponse> courseResponses = wishListService.addCourseToUserWishList(courseId);
        var response = ApiResponse.<List<CourseResponse>>builder()
                .message("add course to user's wish list success")
                .result(courseResponses)
                .build();
        return courseResponses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> deleteCourseInCartForUser(@PathVariable(name = "id") String id) {
        int courseId = Integer.parseInt(id);
        List<CourseResponse> courseResponses = wishListService.removeCourseFromUserWishList(courseId);
        var response = ApiResponse.<List<CourseResponse>>builder()
                .message("remove course from user's wish list success")
                .result(courseResponses)
                .build();
        return courseResponses.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }
}
