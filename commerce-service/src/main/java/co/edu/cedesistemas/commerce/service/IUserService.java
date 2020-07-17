package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;

import java.util.List;

public interface IUserService {
    User createUser(final User user);
    User getById(final String id);
    List<User> getByEmail(final String email);
    User updateUser(String id, User user) throws Exception;
    void deleteUser(String id);
}
