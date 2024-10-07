package net.branium.dtos.lecture;

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
    private LectureType type;
    private String resource;
    private long duration;
}
