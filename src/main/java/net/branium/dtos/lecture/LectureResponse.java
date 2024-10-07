package net.branium.dtos.lecture;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.cfg.EnumFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.branium.domains.LectureType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureResponse {
    private int id;
    private String title;
    private int order;
    @JsonSerialize(using = LectureTypeSerializer.class)
    private LectureType type;
    private String resource;
    private String duration;
}
