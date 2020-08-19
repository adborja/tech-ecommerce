package co.edu.cedesistemas.commerce.registration.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.model.User.Status;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
	
	private final UserRepository repository;
	private final EventPublisherService publisherService;

	@Cacheable(cacheNames = "registration-user", key = "#id")
	public User getById(final String id) {
    	log.info("getting user by id {}",id);

		Optional<User> user = repository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	public User createUser(User user) {
    	log.info("creating user");
		user.setId(UUID.randomUUID().toString());
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		user.setStatus(Status.ACTIVE);
		User created = repository.save(user);
        publisherService.publishRegistrationEvent(created, RegistrationEvent.Status.USER_CREATED);
		return created;
	}

	public User updateUserStatus(String id, String active) {
		log.info("updating user {} with status {}",id,active);
		User found = getById(id);
        if (found == null) {
            return null;
        }
        Status status = active.equals("activate") ? Status.ACTIVE : found.getStatus();
        
        found.setStatus(status);
        return repository.save(found);
	}
   

    public void deleteUser(final String id) {
        User found = getById(id);
        if (found != null) {
            repository.deleteById(id);
            publisherService.publishRegistrationEvent(found, RegistrationEvent.Status.USER_DELETED);
        }
    }
}
