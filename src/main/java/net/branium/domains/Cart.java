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
@Table(name = "`cart`")
public class Cart {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId("id")
    private User user;

//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
//    private Set<CartItem> cartItems = new HashSet<>();
}
