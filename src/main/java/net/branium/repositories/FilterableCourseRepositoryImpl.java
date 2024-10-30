package net.branium.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import net.branium.domains.Course;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FilterableCourseRepositoryImpl implements FilterableCourseRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<Course> listWithFilter(Pageable pageable, Map<String, Object> filterFields) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

        Root<Course> root = criteriaQuery.from(Course.class);

        List<Order> listOrder = new ArrayList<>();

        pageable.getSort().stream().forEach(order -> {
            System.out.println("Order field: " + order.getProperty());
            Order ord = criteriaBuilder.asc(root.get(order.getProperty()));
            listOrder.add(ord);
        });

        criteriaQuery.orderBy(listOrder);

        TypedQuery<Course> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Course> listResult = typedQuery.getResultList();

        int totalRows = 0;
        return new PageImpl<>(listResult, pageable, totalRows);
    }
}
