package net.branium.repositories;

import net.branium.domains.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    @Query(value = "SELECT u FROM UserAnswer u WHERE u.user.id = ?1 AND u.question.id = ?2")
    Optional<UserAnswer> findUserAnswerByUserIdAndQuestionId(String userId, int questionId);
}
