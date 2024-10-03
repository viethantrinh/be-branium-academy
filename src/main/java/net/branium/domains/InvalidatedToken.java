package net.branium.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`invalidated_token`")
public class InvalidatedToken {
    @Id
    @Column(name = "jwtid", nullable = false, unique = true, length = 128)
    private String jwtid;

    @Column(name = "expiration_time", nullable = false)
    private Date expirationTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvalidatedToken that = (InvalidatedToken) o;
        return Objects.equals(jwtid, that.jwtid) && Objects.equals(expirationTime, that.expirationTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(jwtid);
        result = 31 * result + Objects.hashCode(expirationTime);
        return result;
    }
}
