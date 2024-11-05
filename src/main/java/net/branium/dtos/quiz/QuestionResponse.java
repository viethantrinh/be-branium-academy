package net.branium.dtos.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "answer1",
        "isCorrect1",
        "answer2",
        "isCorrect2",
        "answer3",
        "isCorrect3"
})
public class QuestionResponse {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("answer1")
    private String answer1;

    @JsonProperty("isCorrect1")
    private boolean isCorrect1;

    @JsonProperty("answer2")
    private String answer2;

    @JsonProperty("isCorrect2")
    private boolean isCorrect2;

    @JsonProperty("answer3")
    private String answer3;

    @JsonProperty("isCorrect3")
    private boolean isCorrect3;
}
