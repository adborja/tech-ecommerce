package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Profile("!" + SpringProfile.SANDBOX)
public class UserService implements IUserService{
    private final UserRepository repository;

    public User createUser(final User user) {
         return repository.save(user);
    }

    public User updateUser(final User user) {
       User updated = getUser(user.getId()).get();
       BeanUtils.copyProperties(user,updated, Utils.getNullPropertyNames(user));
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
