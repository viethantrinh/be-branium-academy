package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.*;
import net.branium.dtos.course.CourseResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.mappers.CourseMapper;
import net.branium.repositories.*;
import net.branium.services.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final WishListRepository wishListRepo;
    private final OrderRepository orderRepo;
    private final CourseMapper courseMapper;

    @Override
    public long getCartQuantities(String userid) {
        long quantity = cartRepo.countTotalCartItems(userid);
        return quantity;
    }

    @Override
    public List<CourseResponse> getCoursesFromUserCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        List<CartItem> cartItems = cartRepo.findCartItemsByUserId(userId);
        List<CourseResponse> courseResponses = new ArrayList<>();
        cartItems.forEach((ci) -> {
            CourseResponse courseResponse = courseMapper.toCourseResponse(ci.getCourse());
            courseResponses.add(courseResponse);
        });
        return courseResponses;
    }

    @Override
    public List<CourseResponse> addCourseToUserCart(int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // find the user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // find the course by its id
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        // check if this cart item already existed
        if (cartRepo.isCourseExistedInUserCart(user.getId(), course.getId())) {
            throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
        }

        // check if the course is bought by user yet
        if (orderRepo.isUserPaid(userId, OrderStatus.SUCCEEDED, course.getId())) {
            throw new ApplicationException(ErrorCode.UNCATEGORIZED_ERROR);
        }

        // check if the course is in the wish list or not.
        if (wishListRepo.isCourseExistedInUserWishList(user.getCart().getId(), course.getId())) {
            // If yes, remove the course associated with the wish list
            wishListRepo.deleteByWishListIdAndCourseId(user.getCart().getId(), course.getId());
        }


        // If no, just add the course straight to the cart
        CartItem newCartItem = CartItem.builder()
                .cart(user.getCart())
                .course(course)
                .build();
        user.getCart().getCartItems().add(newCartItem);
        userRepo.save(user);

        // get courses from user cart
        return getCoursesFromUserCart();
    }

    @Override
    public List<CourseResponse> removeCourseFromUserCart(int courseId) {
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

        cartRepo.deleteByCartIdAndCourseId(user.getCart().getId(), course.getId());
        return getCoursesFromUserCart();
    }


}
