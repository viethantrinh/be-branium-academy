package net.branium.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`quiz`")
public class Quiz {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId("id")
    private Lecture lecture;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "quiz")
    private Set<QuizResult> quizResults = new HashSet<>();
}
