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
import java.util.UUID;

@Profile("!"+ SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService{
    private final UserRepository repository;

    public User createUser(final User user) {
        log.info("creating User {}", user.getName());
        user.setId(UUID.randomUUID().toString());
        return repository.save(user);
    }

    public User getById(final String id) {
        log.info("retrieving Product by Id {}", id);
        return repository.findById(id).get();
    }

    public List<User> getByEmail(final String email) {
        log.info("retrieving Product by email {}", email);
        return repository.findByEmailLike(email);
    }

    public User updateUser(String id, User user) {
        User userFind = repository.findById(id).get();
        if(userFind == null){
            log.warn("User not found: {}", id);
        }
        BeanUtils.copyProperties(user,userFind, Utils.getNullPropertyNames(user));
        return repository.save(userFind);
    }

    public void deleteUser(String id) {
        log.info("deleting User by Id {}", id);
        repository.deleteById(id);
    }
}
