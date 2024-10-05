package net.branium.mappers;

import net.branium.domains.Course;
import net.branium.dtos.course.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    CourseResponse toCourseResponse(Course course);

}