package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.constants.ApplicationConstants;
import net.branium.controllers.CourseController;
import net.branium.domains.*;
import net.branium.dtos.course.CourseDetailResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.mappers.CourseMapper;
import net.branium.repositories.*;
import net.branium.services.CourseService;
import net.branium.utils.ResourceUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final WishListRepository wishListRepo;
    private final CourseMapper courseMapper;

    @Override
    public CollectionModel<CourseResponse> getAllCourses(int page, int size, String sort, Map<String, Object> filterFields) {
        int pageNumber = page;
        int pageSize = size;
        Sort sortBy = null;
        switch (sort) {
            case "priceDesc" -> sortBy = Sort.by("price").descending();
            case "priceAsc" -> sortBy = Sort.by("price").ascending();
            default -> sortBy = Sort.by("buyCount", "studyCount").descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy);
        Page<Course> pages = courseRepo.listWithFilter(pageable, filterFields);
        List<Course> courses = pages.getContent();
        List<CourseResponse> courseResponses = courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
        return addPageMetaData(courseResponses, pages, sort, filterFields);
    }


    private CollectionModel<CourseResponse> addPageMetaData(List<CourseResponse> courseResponses,
                                                            Page<Course> pageInfo, String sort, Map<String, Object> filterFields) {
        int pageSize = pageInfo.getSize();
        int pageNum = pageInfo.getNumber() + 1;
        long totalElements = pageInfo.getTotalElements();
        long totalPages = pageInfo.getTotalPages();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageSize, pageNum, totalElements);
        CollectionModel<CourseResponse> collectionModel = PagedModel.of(courseResponses, pageMetadata);

        String actualCategory = filterFields.get("category") == null ? "" : ((Category) filterFields.get("category")).getTitle();
        String actualKeyword = filterFields.get("title") == "" ? "" : (String) filterFields.get("title");


        // add links to collection its self
        collectionModel
                .add(
                        linkTo(methodOn(CourseController.class).searchForCourses(pageNum, pageSize, sort, actualCategory, actualKeyword))
                                .withSelfRel()
                );

        // add links to first page if the current page is not 1 (the first one)
        if (pageNum > 1) {
            collectionModel.add(

                    linkTo(methodOn(CourseController.class).searchForCourses(1, pageSize, sort, actualCategory, actualKeyword))
                            .withRel(IanaLinkRelations.FIRST)
            );

            // add links to previous page if the current page is not 1 (the first one)
            collectionModel.add(
                    linkTo(methodOn(CourseController.class).searchForCourses(pageNum - 1, pageSize, sort, actualCategory, actualKeyword))
                            .withRel(IanaLinkRelations.PREVIOUS)
            );
        }

        if (pageNum < totalPages) {
            collectionModel.add(
                    linkTo(methodOn(CourseController.class).searchForCourses(pageNum + 1, pageSize, sort, actualCategory, actualKeyword))
                            .withRel(IanaLinkRelations.NEXT)
            );
            collectionModel.add(
                    linkTo(methodOn(CourseController.class).searchForCourses((int) totalPages, pageSize, sort, actualCategory, actualKeyword))
                            .withRel(IanaLinkRelations.LAST)
            );
        }

        return collectionModel;
    }

    @Override
    public List<CourseResponse> getAllPopularCourses() {
        List<Course> courses = courseRepo.findByStudyCountDescAndBuyCountDesc();
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public long getTotalStudentsEnrolled(int courseId) {
        long totalStudents = courseRepo.countTotalStudentsEnrolledById(courseId);
        return totalStudents;
    }

    @Override
    public List<CourseResponse> getAllCoursesBoughtByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Order> orders = orderRepo.findByUserIdAndOrderStatus(authentication.getName(), OrderStatus.SUCCEEDED);
        List<Course> courses = new ArrayList<>();
        for (Order order : orders) {
            List<Course> boughtCourses = courseRepo.findByOrderDetailsOrderId(order.getId());
            courses.addAll(boughtCourses);
        }

        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public CourseDetailResponse getCourseDetails(int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        CourseDetailResponse response = courseMapper.toCourseDetailResponse(course);

        int totalStudents = (int) getTotalStudentsEnrolled(courseId);
        int totalSections = (int) courseRepo.countTotalSectionsByCourseId(courseId);
        int totalLectures = (int) courseRepo.countTotalLecturesByCourseId(courseId);
        String totalDuration = getTotalCourseDuration(courseId);
        boolean paid = orderRepo.isUserPaid(userId, OrderStatus.SUCCEEDED, courseId);
        boolean enrolled = enrollmentRepo.isUserEnrolled(userId, courseId);
        boolean inCart = cartRepo.isCourseExistedInUserCart(userId, course.getId());
        boolean inWishList = wishListRepo.isCourseExistedInUserWishList(userId, course.getId());

        response.setTotalStudents(totalStudents);
        response.setTotalSections(totalSections);
        response.setTotalLectures(totalLectures);
        response.setTotalDuration(totalDuration);
        response.setPaid(paid);
        response.setEnrolled(enrolled);
        response.setInCart(inCart);
        response.setInWishList(inWishList);
        response.getSections().forEach((s) -> {
            s.getLectures().forEach((l) -> {
                if (l.getType() == LectureType.VIDEO) {
                    // TODO: change the path of video later with id of lecture (now use 1 video for test)
                    Path path = Path.of(ApplicationConstants.LECTURE_VIDEO_RESOURCE_PATH).resolve(l.getResource());
                    double durationDouble = getLectureVideoDuration(path);
                    l.setDuration(ResourceUtils.formatVideoDurationToMMSS((long) durationDouble));

                    // set the resource to download video
                    String fileCode = l.getResource().substring(0, 8);
                    l.setResource(ResourceUtils.buildDownloadUrl(fileCode, ResourceType.VIDEO));
                }
            });
        });


        return response;
    }

    // TODO: change the path of video later with id of lecture (now use 1 video for test)
    private String getTotalCourseDuration(int courseId) {
        List<Lecture> lectures = courseRepo.findLecturesByCourseId(courseId);
        double totalSeconds = 0L;

        if (lectures.isEmpty()) {
            return null;
        }

        for (Lecture lecture : lectures) {
            if (lecture.getType() == LectureType.VIDEO) {
                Path path = Path.of(ApplicationConstants.LECTURE_VIDEO_RESOURCE_PATH).resolve(lecture.getResource());
                double durationInSeconds = getLectureVideoDuration(path);
                totalSeconds += durationInSeconds;
            }
        }

        return ResourceUtils.formatVideoDurationToHoursAndMinutes((long) totalSeconds);
    }

    private double getLectureVideoDuration(Path path) {
        FrameGrab grab = null;
        try {
            grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(path.toFile()));
        } catch (IOException | JCodecException e) {
            throw new RuntimeException(e);
        }
        double durationInSeconds = grab.getVideoTrack().getMeta().getTotalDuration();
        return durationInSeconds;
    }

    @Override
    public List<CourseResponse> getAllCoursesByCategoryId(int categoryId) {
        List<Course> courses = courseRepo.findByCategoryId(categoryId);
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public boolean enrollInCourse(int courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // find the user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // find the course by its id
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        // check if user has bought this course yet
        if (!orderRepo.isUserPaid(user.getId(), OrderStatus.SUCCEEDED, course.getId())) {
            return false;
        }

        // check if user is already enrolled in this course => not save to database
        if (enrollmentRepo.isUserEnrolled(user.getId(), course.getId())) {
            return false;
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .build();

        enrollmentRepo.save(enrollment);
        return true;
    }

    @Override
    public int increaseStudyCount(int courseId) {
        // find the course by its id
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));
        course.setStudyCount(course.getStudyCount() + 1);
        Course savedCourse = courseRepo.save(course);
        return savedCourse.getStudyCount();
    }

    @Override
    public int increaseBuyCount(int courseId) {
        // find the course by its id
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));
        course.setStudyCount(course.getBuyCount() + 1);
        Course savedCourse = courseRepo.save(course);
        return savedCourse.getStudyCount();
    }
}
