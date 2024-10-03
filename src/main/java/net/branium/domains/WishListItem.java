package net.branium.domains;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`wish_list_item`")
public class WishListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wish_list_id")
    private WishList wishList;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
