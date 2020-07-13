package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User createUser(User user);

    User getById(String id);

    List<User> getByMail(String mail);

    void  deleteUser(String id);

    User updateUser(String id, User user) throws Exception;
}
