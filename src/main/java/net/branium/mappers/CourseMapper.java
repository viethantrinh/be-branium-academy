package net.branium.mappers;

import net.branium.dtos.course.CourseBA;
import net.branium.dtos.course.CourseItemResponse;
import net.branium.dtos.section.SectionBA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mappings({
            @Mapping(target = "discountPercent",
                    expression = "java(getDiscountPercent(courseBA.getSalePrice(), courseBA.getPrice()))"),
            @Mapping(target = "totalLecture",
                    expression = "java(getTotalLecture(courseBA.getSections()))")
    })
    CourseItemResponse toCourseItemResponse(CourseBA courseBA);

    default Double getDiscountPercent(Double salePrice, Double originalPrice) {
        if (salePrice != 0 && originalPrice > salePrice) {
            return (salePrice / originalPrice) * 100;
        }
        return 0.0d;
    }

    default Integer getTotalLecture(List<SectionBA> sectionBAList) {
        long count = 0;
        if (sectionBAList != null && !sectionBAList.isEmpty()) {
            for (SectionBA sectionBA : sectionBAList) {
                count += sectionBA.getLectures().size();
            }
            return Math.toIntExact(count);
        }
        return 0;
    }
}
