package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    User deleteUserById(String id);
    User updateUserById(String id, User user);
    List<User> getUserByEmail(String email);
    User getUserById(String id);
}
