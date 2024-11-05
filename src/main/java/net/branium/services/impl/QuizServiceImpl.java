package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.Quiz;
import net.branium.dtos.quiz.QuestionResponse;
import net.branium.dtos.quiz.QuizResponse;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.repositories.QuizRepository;
import net.branium.services.QuizService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    public QuizResponse getQuizDetailsById(int quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED)
        );
        QuizResponse.QuizResponseBuilder quizResponseBuilder = QuizResponse.builder();
        QuizResponse quizResponse = quizResponseBuilder
                .id(quiz.getId())
                .title(quiz.getLecture().getTitle())
                .questionCount(quiz.getQuestions().size())
                .description(quiz.getDescription())
                .questionResponses(
                        quiz.getQuestions().stream().map((question) -> QuestionResponse.builder()
                                .id(question.getId())
                                .title(question.getTitle())
                                .answer1(question.getAnswer1())
                                .isCorrect1(question.isCorrect1())
                                .answer2(question.getAnswer2())
                                .isCorrect2(question.isCorrect2())
                                .answer3(question.getAnswer3())
                                .isCorrect3(question.isCorrect3())
                                .build()).toList()
                )
                .build();
        return quizResponse;
    }


}
