package net.branium.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import net.branium.domains.Course;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FilterableCourseRepositoryImpl implements FilterableCourseRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Course> listWithFilter(Pageable pageable, Map<String, Object> filterFields) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> root = criteriaQuery.from(Course.class);

        // filter
        Predicate[] predicates = createPredicates(filterFields, criteriaBuilder, root);
        if (predicates.length > 0) criteriaQuery.where(predicates);

        // sort
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
        long totalRows = getTotalRows(filterFields);
        return new PageImpl<>(listResult, pageable, totalRows);
    }

    private Predicate[] createPredicates(Map<String, Object> filterFields, CriteriaBuilder criteriaBuilder, Root<Course> root) {
        // filter
        Predicate[] predicates = new Predicate[filterFields.size()];
        if (!filterFields.isEmpty()) {
            Iterator<String> iterator = filterFields.keySet().iterator();
            int i = 0;
            while (iterator.hasNext()) {
                String fieldName = iterator.next();
                Object fieldValue = filterFields.get(fieldName);
                System.out.println("fieldName =>" + fieldValue);
                if (fieldName.equals("category")) {
                    predicates[i] = criteriaBuilder.equal(root.get(fieldName), fieldValue);
                } else if (fieldName.equals("title")) {
                    predicates[i] = criteriaBuilder.like(root.get(fieldName), "%" + fieldValue + "%");
                }
                i++;
            }
        }
        return predicates;
    }

    private long getTotalRows(Map<String, Object> filterFields) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Course> root = criteriaQuery.from(Course.class);

        criteriaQuery.select(criteriaBuilder.count(root));
        Predicate[] predicates = createPredicates(filterFields, criteriaBuilder, root);

        if (predicates.length > 0) criteriaQuery.where(predicates);

        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        return rowCount;
    }
}
