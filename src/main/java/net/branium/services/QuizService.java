package net.branium.services;

import net.branium.dtos.quiz.QuizResponse;

public interface QuizService {
    QuizResponse getQuizDetailsById(int quizId);
}
