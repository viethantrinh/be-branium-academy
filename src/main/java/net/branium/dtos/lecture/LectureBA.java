package net.branium.dtos.lecture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LectureBA {

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "duration")  // original model use Integer
    private String duration;
}
