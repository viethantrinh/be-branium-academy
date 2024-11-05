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
@JsonPropertyOrder({"questionId", "selectedOption", "isCorrect"})
public class QuestionSubmitRequest {
    @JsonProperty("questionId")
    private int questionId;

    @JsonProperty("selectedOption")
    private int selectedOption;

    @JsonProperty("isCorrect")
    private boolean isCorrect;
}
