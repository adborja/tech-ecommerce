package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("!" + SpringProfile.SANDBOX)
@AllArgsConstructor
@Service
public class UserService implements IUserService{
    private UserRepository repository;


    public User createUser(User user) {
        return repository.save(user);
    }

    public User deleteUserById(String id) {
        Optional<User> optional = repository.findById(id);
        if(optional.isEmpty()) return null;
        else {
            User user = optional.get();
            repository.deleteById(id);
            return user;
        }
    }

    public User updateUserById(String id, User user) {
        Optional<User> optional = repository.findById(id);
        if(optional.isEmpty()) return null;
        else {
            User userUpdated = optional.get();
            BeanUtils.copyProperties(user,userUpdated, Utils.getNullPropertyNames(user));
            repository.save(userUpdated);
            return userUpdated;
        }
    }

    public List<User> getUserByEmail(String email) {
        return repository.findByEmailLike(email);
    }

    public User getUserById(String id) {
        return repository.findById(id).get();
    }
}
