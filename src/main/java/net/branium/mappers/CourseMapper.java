package net.branium.mappers;

import net.branium.domains.Course;
import net.branium.domains.ResourceType;
import net.branium.dtos.course.CourseHomeResponse;
import net.branium.dtos.course.CourseResponse;
import net.branium.utils.ResourceUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    @Mapping(target = "image", expression = "java(buildImageLink(course))")
    CourseResponse toCourseResponse(Course course);


    default String buildImageLink(Course course) {
        String imageFileName = course.getImage();
        if (imageFileName == null) {
            return null;
        }
        String fileCode = imageFileName.substring(0, 8);
        String url = ResourceUtils.buildDownloadUrl(fileCode, ResourceType.IMAGE);
        return url;
    }
}
