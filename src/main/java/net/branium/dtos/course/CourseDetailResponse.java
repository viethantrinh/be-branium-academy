package net.branium.dtos.course;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.branium.dtos.section.SectionResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "title", "image", "shortDescription", "fullDescription", "price", "discountPrice",
        "updatedAt", "totalStudents", "totalSections", "totalLectures", "totalDuration", "paid", "inCart",
        "inWishList", "sections"})
public class CourseDetailResponse extends CourseResponse {
    private String shortDescription;
    private String fullDescription;
    private LocalDateTime updatedAt;
    private int totalStudents;
    private int totalSections;
    private int totalLectures;
    private int totalDuration;
    private boolean paid;
    private boolean isEnrolled;
    private boolean inCart;
    private boolean inWishList;
    private List<SectionResponse> sections = new ArrayList<>();
}
