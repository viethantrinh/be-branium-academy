package net.branium.mappers;

import net.branium.domains.Category;
import net.branium.domains.ResourceType;
import net.branium.dtos.category.CategoryResponse;
import net.branium.utils.ResourceUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(target = "image", expression = "java(buildImageLink(category))")
    CategoryResponse toCategoryResponse(Category category);

    default String buildImageLink(Category category) {
        String imageFileName = category.getImage();
        if (imageFileName == null) {
            return null;
        }
        String fileCode = imageFileName.substring(0, 8);
        String url = ResourceUtils.buildDownloadUrl(fileCode, ResourceType.IMAGE);
        return url;
    }
}
