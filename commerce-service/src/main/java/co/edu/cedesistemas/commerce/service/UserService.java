package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final UserRepository repository;

    public User createUser(final User user) {
        log.info("create user {}", user);
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

    public User updateUser(String id, User user) {
        User userToUpdate = repository.findById(id).get();
        BeanUtils.copyProperties(user, userToUpdate, Utils.getNullPropertyNames(user));
        return repository.save(userToUpdate);
    }




}
