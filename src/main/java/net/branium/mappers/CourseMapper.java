package net.branium.mappers;

import net.branium.domains.Course;
import net.branium.dtos.course.CourseCreateRequest;
import net.branium.dtos.course.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {


    @Mapping(target = "category", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "studyCount", ignore = true)
    @Mapping(target = "sections", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "buyCount", ignore = true)
    Course toCourse(CourseCreateRequest request);
    CourseResponse toCourseResponse(Course course);

}