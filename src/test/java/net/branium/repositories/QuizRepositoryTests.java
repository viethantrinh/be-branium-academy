package net.branium.repositories;

import net.branium.domains.Quiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"oracle"})
class QuizRepositoryTests {

    @Autowired
    private QuizRepository quizRepository;


    @Test
    void testGetQuizDataSuccess() {
        Optional<Quiz> quizOptional = quizRepository.findById(21);
        quizOptional.ifPresent(System.out::println);
    }
}
