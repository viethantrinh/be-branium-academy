package net.branium.services;

import net.branium.domains.User;

import java.util.List;


public interface IUserService {
    User create(User user);

    User getById(String id);

    User getByEmail(String email);

    List<User> list();

    User update(String id, User user);

    void delete(String id);

    boolean existsByEmail(String email);

    User createCustomer(User user);

    User getCustomerInfo();

    User updateCustomer(String id, User user);
}
