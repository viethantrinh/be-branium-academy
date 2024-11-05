package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.quiz.QuizResponse;
import net.branium.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<QuizResponse>> getQuizDetailsById(@PathVariable(name = "id") int id) {
        QuizResponse quiz = quizService.getQuizDetailsById(id);
        ApiResponse<QuizResponse> response = ApiResponse.<QuizResponse>builder()
                .message("get quiz details success")
                .result(quiz)
                .build();
        return ResponseEntity.ok(response);
    }
}
