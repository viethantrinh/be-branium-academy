package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.*;
import net.branium.dtos.course.CourseResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.mappers.CourseMapper;
import net.branium.repositories.*;
import net.branium.services.WishListService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final WishListRepository wishListRepo;
    private final OrderRepository orderRepo;
    private final CourseMapper courseMapper;


    @Override
    public List<CourseResponse> getCoursesFromUserWishList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        List<WishListItem> wishListItems = wishListRepo.findWishListItemsByUserId(userId);
        List<CourseResponse> courseResponses = new ArrayList<>();
        wishListItems.forEach((wli) -> {
            CourseResponse courseResponse = courseMapper.toCourseResponse(wli.getCourse());
            courseResponses.add(courseResponse);
        });
        return courseResponses;
    }

    @Override
    public List<CourseResponse> addCourseToUserWishList(int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // find the user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // find the course by its id
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        // check if this wish list item already existed
        if (wishListRepo.isCourseExistedInUserWishList(user.getId(), course.getId())) {
            throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
        }

        // check if the course is bought by user yet
        if (orderRepo.isUserPaid(userId, OrderStatus.SUCCEEDED, course.getId())) {
            throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
        }

        // check if the course is in the cart or not.
        if (cartRepo.isCourseExistedInUserCart(user.getWishList().getId(), course.getId())) {
            // If yes, remove the course associated with the cart
            cartRepo.deleteByCartIdAndCourseId(user.getWishList().getId(), course.getId());
        }

        // If no, just add the course straight to the wish list
        WishListItem newWishListItem = WishListItem.builder()
                .wishList(user.getWishList())
                .course(course)
                .build();
        user.getWishList().getWishListItems().add(newWishListItem);
        userRepo.save(user);

        // get courses from user's wish list
        return getCoursesFromUserWishList();
    }

    @Override
    public List<CourseResponse> removeCourseFromUserWishList(int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // find the user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // find the course by its id
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        // check if the course is bought by user yet
        if (orderRepo.isUserPaid(userId, OrderStatus.SUCCEEDED, course.getId())) {
            throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
        }

        wishListRepo.deleteByWishListIdAndCourseId(user.getWishList().getId(), course.getId());
        return getCoursesFromUserWishList();
    }


}
