package net.branium.dtos.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Integer id;
    private String title;
    private String image;
    private String shortDescription;
    private String fullDescription;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int studyCount;
    private int buyCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}