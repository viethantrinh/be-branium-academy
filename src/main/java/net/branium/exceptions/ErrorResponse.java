package net.branium.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"status", "time_stamp", "path", "errors"})
public class ErrorResponse {
    @JsonProperty(value = "status")
    private int status;

    @JsonProperty(value = "time_stamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeStamp;

    @JsonProperty(value = "path")
    private String path;

    @JsonProperty(value = "errors")
    private List<String> errors = new ArrayList<>();
}
