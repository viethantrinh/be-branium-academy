package net.branium.dtos.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequest {
    private String title;
    private String shortDescription;
    private String fullDescription;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int buyCount;
    private int studyCount;
}
