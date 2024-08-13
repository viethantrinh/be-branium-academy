package net.branium.domains;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "invalidated_token", schema = "han_branium_academy")
@EntityListeners(AuditingEntityListener.class)
public class InvalidatedToken {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "token")
    private String JWTID;

    @Column(name = "expiration_time")
    private Date expirationTime;
}
