package net.branium.domains;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`question`")
@EntityListeners({AuditingEntityListener.class})
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "title", nullable = false, unique = true, length = 128)
    private String title;

    @Column(name = "answer_1")
    private String answer1;

    @Column(name = "is_correct_1")
    private boolean isCorrect1;

    @Column(name = "answer_2")
    private String answer2;

    @Column(name = "is_correct_2")
    private boolean isCorrect2;

    @Column(name = "answer_3")
    private String answer3;

    @Column(name = "is_correct_3")
    private boolean isCorrect3;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private Set<UserAnswer> userAnswers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", isCorrect1=" + isCorrect1 +
                ", answer2='" + answer2 + '\'' +
                ", isCorrect2=" + isCorrect2 +
                ", answer3='" + answer3 + '\'' +
                ", isCorrect3=" + isCorrect3 +
                '}';
    }
}
