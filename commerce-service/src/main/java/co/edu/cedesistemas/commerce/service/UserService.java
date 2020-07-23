package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService implements IUserService {
    private UserRepository repository;


    public User createUser(User user) {
        return repository.save(user);
    }

    public User deleteUserById(String id) {
        User userdeleted = repository.findById(id).orElse(null);;
        if(userdeleted==null) return null;
        else {

            repository.deleteById(id);
            return userdeleted;
        }
    }

    public User updateUserById(String id, User user) {
        User userUpdated = repository.findById(id).orElse(null);;
        if(userUpdated==null) return null;
        else {

            BeanUtils.copyProperties(user,userUpdated, Utils.getNullPropertyNames(user));
            repository.save(userUpdated);
            return userUpdated;
        }
    }

    public List<User> getUserByEmail(String email) {
        return repository.findByEmailLike(email);
    }

    public User getUserById(String id) {
        return repository.findById(id).orElse(null);
    }
}