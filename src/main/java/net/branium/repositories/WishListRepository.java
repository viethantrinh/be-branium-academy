package net.branium.repositories;

import net.branium.domains.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, String> {


    @Query("""
            select (count(w) > 0) 
            from WishList w inner join w.wishListItems wishListItems
            where 
                w.user.id = ?1 
                and 
                wishListItems.course.id = ?2""")
    boolean isCourseExistedInUserWishList(String userId, int courseId);


    @Transactional
    @Modifying
    @Query("delete from WishListItem wli where wli.wishList.id = ?1 and wli.course.id = ?2")
    void deleteByWishListIdAndCourseId(String wishListId, int courseId);

}
