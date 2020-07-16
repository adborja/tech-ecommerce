package co.edu.cedesistemas.commerce.registration.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.model.User.Status;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private final UserRepository repository;

	public User getById(final String id) {
		Optional<User> user = repository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	public User createUser(User user) {
		user.setId(UUID.randomUUID().toString());
		user.setCreatedAt(LocalDateTime.now());
		return repository.save(user);
	}

	public User updateUserStatus(String id, String active) {
		User found = getById(id);
        if (found == null) {
            return null;
        }
        Status status = active.equals("activate") ? Status.ACTIVE : found.getStatus();
        
        found.setStatus(status);
        return repository.save(found);
	}
}
