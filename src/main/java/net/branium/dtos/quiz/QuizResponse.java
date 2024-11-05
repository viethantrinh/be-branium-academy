package net.branium.dtos.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResponse {
    private int id;
    private String title;
    private String description;
    private int questionCount;
    private List<QuestionResponse> questionResponses = new ArrayList<>();
}
