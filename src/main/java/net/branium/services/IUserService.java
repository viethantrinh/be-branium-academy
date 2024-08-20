package net.branium.services;

import net.branium.domains.User;

import java.util.List;


public interface IUserService {
    User create(User user);

    User getById(String id);

    List<User> list();

    User update(String id, User user);

    void delete(String id);

    User getCustomerInfo();

    User updateCustomer(String id, User user);

}
