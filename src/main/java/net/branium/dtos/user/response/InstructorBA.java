package net.branium.dtos.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstructorBA {

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "description")
    private String detail;
}
