package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(User user);

    Optional<User> getUser(String id);

    Optional<User>  getUserbyEmail(String email);


}
