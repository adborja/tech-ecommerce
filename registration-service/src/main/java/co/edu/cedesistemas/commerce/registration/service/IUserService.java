package co.edu.cedesistemas.commerce.registration.service;


import co.edu.cedesistemas.commerce.registration.model.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    User getById(String id);
    void deleteUser(String id);
    List<User> getByEmail(String email);
    User updateUser(String id, User user) throws Exception;

}
