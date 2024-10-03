package net.branium.domains;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`lecture`")
@EntityListeners({AuditingEntityListener.class})
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "title", nullable = false, unique = true, length = 128)
    private String title;

    @Column(name = "ord", nullable = false, unique = true)
    private int order;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ResourceType type;

    @Column(name = "video", unique = true)
    private String video;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToOne(mappedBy = "lecture", cascade = CascadeType.ALL)
    private Quiz quiz;
}
