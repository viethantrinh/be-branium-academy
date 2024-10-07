package net.branium.dtos.section;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.dtos.lecture.LectureResponse;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"id", "title", "order", "lectures"})
public class SectionResponse {
    private int id;
    private String title;
    private int order;
    private List<LectureResponse> lectures = new ArrayList<>();
}
