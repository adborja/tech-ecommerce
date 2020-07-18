package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;

import java.util.List;

public interface IUserService {

    public User createUser(final User User);
    public User getById(final String id);
    public List<User> getByEmail(final String email);
    public void deleteUserById (final String id);
    public User updateUser (final String id, final User User);
}
