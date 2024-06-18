package net.branium.dtos.section.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.branium.dtos.lecture.response.LectureBA;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectionBA {

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String shortDescription;

    @JsonProperty(value = "items")
    private List<LectureBA> lectures = new ArrayList<>();


}
