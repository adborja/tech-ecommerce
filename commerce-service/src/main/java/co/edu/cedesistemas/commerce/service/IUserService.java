package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(User user);

    Optional<User> findById(String id);

    List<User> findUsersByEmail(String email);

    Optional<User> deleteUserById(String id);

    Optional<User> updateUser(String id, User user) throws Exception;
}
