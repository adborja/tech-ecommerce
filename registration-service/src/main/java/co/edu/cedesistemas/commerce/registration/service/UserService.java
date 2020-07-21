package co.edu.cedesistemas.commerce.registration.service;


import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    public User createUser(final User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setId(UUID.randomUUID().toString());
        user.setStatus(User.Status.INACTIVE);
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
    public User activeUser(String id) {
        User user = repository.findById(id).orElse(null);
        if (user != null){
            user.setStatus(User.Status.ACTIVE);
            return repository.save(user);
        }else{
            return null;
        }
    }

}
