package net.branium.controllers;

import lombok.RequiredArgsConstructor;
import net.branium.dtos.base.ApiResponse;
import net.branium.dtos.quiz.QuizResponse;
import net.branium.dtos.quiz.QuizSubmitRequest;
import net.branium.dtos.quiz.QuizSubmitResponse;
import net.branium.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/submit")
    public ResponseEntity<ApiResponse<QuizSubmitResponse>> submitAnswer(
            @RequestBody QuizSubmitRequest request
    ) {
        QuizSubmitResponse quizSubmitResponse = quizService.processAnswer(request);
        ApiResponse<QuizSubmitResponse> response = ApiResponse.<QuizSubmitResponse>builder()
                .message("submit quiz success")
                .result(quizSubmitResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
