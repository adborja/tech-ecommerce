package co.edu.cedesistemas.commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService{
	
	private final UserRepository repository;
	@Override
	public List<User> getByEmail(final String email) throws Exception{
		List<User> users = repository.findByEmail(email);
		
		if(users.isEmpty()) 
			throw new Exception("No se encontraron usuarios para el correo "+email);
		
		return users;
	}
	@Override
	public User createUser(final User user) {
		return repository.save(user);
	}
	@Override
	public User getById(final String id) {
		Optional<User> user = repository.findById(id);
		return user.isPresent() ? user.get() : null;
	}
	@Override
	public User updateUser(final String id, final User user) {
		User found = getById(id);
		if(found == null) {
			log.warn("User not found {}",id);
			return null;
		}
		BeanUtils.copyProperties(user, found, Utils.getNullPropertyNames(user));
		return  repository.save(found);
	}
	@Override
	public void deleteUser(final String id) {
		repository.deleteById(id);
	}

}
