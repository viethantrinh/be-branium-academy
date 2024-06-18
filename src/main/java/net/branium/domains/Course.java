package net.branium.domains;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course {
    @EqualsAndHashCode.Include
    private Integer id;                 // id
    private String title;               // name
    private String photo;               // image
    private String shortDescription;   // null
    private String fullDescription;    // content
    private Integer totalLecture;       // TODO: calculate by lecture inside section => handle in CourseMapper
    private Integer totalDuration;      // duration
    private Integer totalStudent;      // count_students
    private Double price;               // origin_price
    private Double discountPercent;     // TODO: calculate by sale_price => handle in CourseMapper
    private Integer retaken_count;      // ratake_count
    private LocalDateTime createdTime = LocalDateTime.now();  // date_created
    private Boolean enabled = true;            // default: true
    private Boolean onSale;             // on_sale
}
