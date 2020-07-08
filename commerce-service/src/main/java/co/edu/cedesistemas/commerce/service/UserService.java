package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User createUser(final User user) {
        return repository.save(user);
    }

    public User getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteUser(final String id) {
        repository.deleteById(id);
    }

    public List<User> getByEmail(final String email) {
        return repository.findByEmail(email);

    }

    public User updateStore(String id, User user) {
        User userToUpdate = repository.findById(id).get();
        BeanUtils.copyProperties(user, userToUpdate, Utils.getNullPropertyNames(user));
        return repository.save(userToUpdate);
    }
}
