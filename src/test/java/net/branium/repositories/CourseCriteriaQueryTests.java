package net.branium.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import net.branium.domains.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles({"mysql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseCriteriaQueryTests {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCriteriaQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = criteriaBuilder.createQuery(Course.class);

        Root<Course> root = query.from(Course.class);

        // Where clause
        Predicate predicate = criteriaBuilder.like(root.get("title"), "%ja%");
        query.where(predicate);

        // Order
        query.orderBy(criteriaBuilder.asc(root.get("title")));

        TypedQuery<Course> typedQuery = entityManager.createQuery(query);

        // Add pagination
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(3);
        List<Course> resultList = typedQuery.getResultList();

        Assertions.assertThat(resultList).isNotEmpty();
        resultList.forEach(System.out::println);
    }

    @Test
    public void testJPQL() {
        String jpql = "FROM Course WHERE title LIKE '%ja%' ORDER BY title";

        TypedQuery<Course> typedQuery = entityManager.createQuery(jpql, Course.class);
        List<Course> resultList = typedQuery.getResultList();

        Assertions.assertThat(resultList).isNotEmpty();
        resultList.forEach(System.out::println);
    }
}
