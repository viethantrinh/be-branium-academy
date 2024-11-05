package net.branium.repositories;

import net.branium.domains.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {

    @Query(value = "SELECT q FROM QuizResult q WHERE q.user.id = ?1 AND q.quiz.id = ?2")
    Optional<QuizResult> findQuizResultByUserIdAndQuizId(String userId, int quizId);
}
