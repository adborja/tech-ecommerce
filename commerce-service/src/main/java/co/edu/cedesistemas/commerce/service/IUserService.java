package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);

    User getById(String id);

    void deleteUser(String id);

    List<User> getByEmail(String email);

    User updateStore(String id, User user);
}
