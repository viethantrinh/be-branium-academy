package net.branium.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "permissions", schema = "han_branium_academy")
public class Permission {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "name", unique = true, nullable = false, length = 128)
    private String name;

    @Column(name = "description")
    private String description;
}
