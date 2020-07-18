package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User createUser(final User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(User.Status.INACTIVE);
        return repository.save(user);
    }

    public User updateUser(final User user) {
       User updated = getUser(user.getId()).get();
       BeanUtils.copyProperties(user,updated, Utils.getNullPropertyNames(user));
       return repository.save(updated);
    }

    public User activateUser(final String id) {
        User updated = getUser(id).get();
        updated.setStatus(User.Status.ACTIVE);
        return repository.save(updated);
    }


    public void deleteUser(final User user) {
        repository.delete(user);
    }

    public Optional<User> getUser(final String id) {
           return repository.findById(id);
    }

    public Optional<User> getUserbyEmail(final String email) {
        return repository.findByEmail(email);
    }

}
