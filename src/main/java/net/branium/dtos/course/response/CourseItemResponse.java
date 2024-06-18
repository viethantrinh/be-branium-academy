package net.branium.dtos.course.response;

import lombok.Data;
import net.branium.domains.Category;
import net.branium.domains.Instructor;

import java.util.List;

@Data
public class CourseItemResponse {
    private Integer id;
    private String title;
    private String photo;
    private Integer totalLecture;
    private Integer totalStudent;
    private Double price;
    private Double discountPercent;
    private Boolean onSale;
    private Instructor instructor;
    private List<Category> categories;

    // phuong thich tinh totalLecture
    // phuong thuc tinh discountPercent
}
