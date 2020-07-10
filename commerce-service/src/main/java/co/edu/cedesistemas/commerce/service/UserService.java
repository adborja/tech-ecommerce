package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    @Override
    public User createUser(final User user) {
        return repository.save(user);
    }

    @Override
    public User getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(final String id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> getByEmail(final String email) {
        return repository.findByEmail(email);

    }

    @Override
    public User updateStore(String id, User user) {
        User userToUpdate = repository.findById(id).get();
        BeanUtils.copyProperties(user, userToUpdate, Utils.getNullPropertyNames(user));
        return repository.save(userToUpdate);
    }
}
