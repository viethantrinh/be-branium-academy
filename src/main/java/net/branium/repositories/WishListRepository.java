package net.branium.repositories;

import net.branium.domains.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

    @Query(value = """
            SELECT 
                (COUNT(*) > 0)
            FROM 
                `USER` u
                INNER JOIN `WISH_LIST` wl ON u.id = wl.id
                INNER JOIN `WISH_LIST_ITEM` wli ON wl.id = wli.wish_list_id
            WHERE 
                u.id = ?1
                AND 
                wli.course_id = ?2
            """, nativeQuery = true)
    long existsByUserIdAndWishListItemsCourseId(String userId, int courseId);
}
