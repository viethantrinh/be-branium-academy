package net.branium.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "roles", schema = "han_branium_academy")
public class Role {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "name", unique = true, nullable = false, length = 128)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_permissions",
            joinColumns = @JoinColumn(name = "roles_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "permissions_name", referencedColumnName = "name"))
    private Set<Permission> permissions = new HashSet<>();
}
