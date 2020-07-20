package co.edu.cedesistemas.commerce.service.interfaces;

import co.edu.cedesistemas.commerce.model.User;

import java.util.List;

public interface IUserService {
    User getById(final String id);
    List<User> getByEmail(String email);
    User updateUser(String id, User user);
    User deleteUser(String id);
    User createUser(User user);
}
