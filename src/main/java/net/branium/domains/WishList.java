package net.branium.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`wish_list`")
public class WishList {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId("id")
    private User user;

    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL)
    private Set<WishListItem> wishListItems = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WishList wishList = (WishList) o;
        return Objects.equals(id, wishList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
