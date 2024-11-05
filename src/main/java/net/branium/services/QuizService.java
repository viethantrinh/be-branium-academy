package net.branium.services;

import net.branium.dtos.quiz.QuizResponse;
import net.branium.dtos.quiz.QuizSubmitRequest;
import net.branium.dtos.quiz.QuizSubmitResponse;

public interface QuizService {
    QuizResponse getQuizDetailsById(int quizId);
    QuizSubmitResponse processAnswer(QuizSubmitRequest request);
}
