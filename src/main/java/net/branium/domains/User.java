package net.branium.domains;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users", schema = "han_branium_academy")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "username", unique = true, length = 128)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 128)
    private String email;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "vip_level")
    private Integer vipLevel;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id")
    )
    private Set<Authority> authorities = new HashSet<>();
}
