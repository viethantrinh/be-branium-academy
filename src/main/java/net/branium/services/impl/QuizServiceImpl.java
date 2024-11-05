package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.*;
import net.branium.dtos.quiz.*;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import net.branium.repositories.QuizRepository;
import net.branium.repositories.QuizResultRepository;
import net.branium.repositories.UserAnswerRepository;
import net.branium.repositories.UserRepository;
import net.branium.services.QuizService;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final QuizResultRepository quizResultRepository;

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

    @Override
    public QuizSubmitResponse processAnswer(QuizSubmitRequest request) {
        List<QuestionSubmitRequest> requestAnswers = request.getAnswers();

        // get the user which answer these questions
        String uuid = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NON_EXISTED));

        // handle the answers of user and save/update each answer to the user_answer table
        processUserAnswer(requestAnswers, user);

        // calculate the total score base on number of right answers / total questions
        // save/update to the quiz_result table
        QuizSubmitResponse response = processQuizResult(request, requestAnswers, user);

        return response;
    }

    private QuizSubmitResponse processQuizResult(QuizSubmitRequest request, List<QuestionSubmitRequest> requestAnswers, User user) {
        Quiz quiz = quizRepository.findById(request.getQuizId()).orElseThrow(
                () -> new ApplicationException(ErrorCode.RESOURCE_NON_EXISTED));

        int totalAnswerRightQuestions = (int) requestAnswers.stream()
                .filter((requestAnswer) -> requestAnswer.isCorrect() == true)
                .count();
        int totalQuestions = quiz.getQuestions().size();
        double totalScore = (double) totalAnswerRightQuestions / totalQuestions * 100;

        QuizResult quizResult = QuizResult.builder()
                .user(user)
                .quiz(quiz)
                .score(totalScore)
                .submittedAt(LocalDateTime.now())
                .build();

        // check if the quiz's result is existed or not
        quizResultRepository.findQuizResultByUserIdAndQuizId(user.getId(), quiz.getId())
                .ifPresentOrElse(
                        (qr) -> {
                            quizResult.setId(qr.getId()); // update the existence quiz result
                        },
                        () -> {
                            // do nothing => that mean creates new
                        }
                );

        QuizResult savedQuizResult = quizResultRepository.save(quizResult);

        QuizSubmitResponse response = QuizSubmitResponse.builder()
                .result(totalAnswerRightQuestions + "/" + totalQuestions)
                .score(savedQuizResult.getScore())
                .time(savedQuizResult.getSubmittedAt())
                .build();

        return response;
    }

    private void processUserAnswer(List<QuestionSubmitRequest> requestAnswers, User user) {
        for (QuestionSubmitRequest requestAnswer : requestAnswers) {
            String userId = user.getId();
            int questionId = requestAnswer.getQuestionId();
            int selectedOption = requestAnswer.getSelectedOption();
            boolean isCorrect = requestAnswer.isCorrect();

            Optional<UserAnswer> answer =
                    userAnswerRepository.findUserAnswerByUserIdAndQuestionId(userId, questionId);

            if (answer.isEmpty()) { // create if empty
                UserAnswer userAnswer = UserAnswer.builder()
                        .selectedOption(selectedOption)
                        .isCorrect(isCorrect)
                        .answeredAt(LocalDateTime.now())
                        .user(user)
                        .question(Question.builder().id(questionId).build())
                        .build();
                userAnswerRepository.save(userAnswer);
                continue;
            }

            // update if not empty
            UserAnswer userAnswer = answer.get();
            userAnswer.setSelectedOption(selectedOption);
            userAnswer.setCorrect(isCorrect);
            userAnswer.setAnsweredAt(LocalDateTime.now());
            userAnswer.setUser(user);
            userAnswer.setQuestion(Question.builder().id(questionId).build());
            userAnswerRepository.save(userAnswer);
        }
    }
}
