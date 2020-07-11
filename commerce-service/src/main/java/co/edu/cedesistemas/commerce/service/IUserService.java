package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;

public interface IUserService {
    User create(User user);
    User getUserById(String id);
    User getUserByEmail(String email);
    User updateUser(User userToUpdate, String id);
    void deleteUser(String userId);
}
