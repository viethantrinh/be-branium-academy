package net.branium.dtos.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.branium.dtos.category.CategoryBA;
import net.branium.dtos.section.SectionBA;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseBA {
    @JsonProperty(value = "id")
    private Integer id;                 // id

    @JsonProperty(value = "name")
    private String title;               // name

    @JsonProperty(value = "image")
    private String photo;               // image

    @JsonProperty(value = "content")
    private String fullDescription;     // content

    @JsonProperty(value = "duration")
    private String totalDuration;       // duration

    @JsonProperty(value = "count_students")
    private Integer totalStudent;       // count_students

    @JsonProperty(value = "origin_price")
    private Double price;               // origin_price

    @JsonProperty(value = "sale_price")
    private Double salePrice;           // sale_price

    @JsonProperty(value = "ratake_count")
    private Integer retake_count;       // ratake_count TODO: for calculate the discountPercent => handle in CourseMapper

    @JsonProperty(value = "date_created")
    private LocalDateTime createdTime;  // date_created

    @JsonProperty(value = "on_sale")
    private Boolean onSale;             // on_sale

    @JsonProperty(value = "categories")
    private List<CategoryBA> categories = new ArrayList<>(); // categories

    @JsonProperty(value = "sections")
    private List<SectionBA> sections = new ArrayList<>();   // sections
}
