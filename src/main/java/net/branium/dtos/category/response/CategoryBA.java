package net.branium.dtos.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryBA {
    @JsonProperty(value = "id")
    private Integer id;     // id

    @JsonProperty(value = "name")
    private String name;    // name
}
