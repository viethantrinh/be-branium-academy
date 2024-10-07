package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.constants.ApplicationConstants;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final WishListRepository wishListRepo;
    private final CourseMapper courseMapper;


    @Override
    public List<CourseResponse> getAllPopularCourses() {
        List<Course> courses = courseRepo.findByStudyCountDescAndBuyCountDesc();
        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepo.findAll();
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
}
