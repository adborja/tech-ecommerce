package co.edu.cedesistemas.commerce.service;

import java.util.List;

import co.edu.cedesistemas.commerce.model.User;

public interface IUserService {
	User createUser(User user);
	void deleteUser(String id);
	List<User> getByEmail(String email) throws Exception;
	User getById(String id);
	User updateUser(String id, User user) throws Exception;
}
