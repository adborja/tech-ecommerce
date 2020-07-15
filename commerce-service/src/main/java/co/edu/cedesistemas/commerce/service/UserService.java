package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private final UserRepository repository;

    public User createUser(final User user){
        user.setId(UUID.randomUUID().toString());
        return repository.save(user);
    }

    public User getById(final String id){
        return repository.findById(id).orElse(null);
    }

    public List<User> getByEmail(final String email){
        return repository.findByEmail(email);
    }

    public User updateUser(final String id, User user){
        User actualUser = getById(id);
        if(actualUser == null){
            return null;
        }
        BeanUtils.copyProperties(user, actualUser, Utils.getNullPropertyNames(user));
        return repository.save(actualUser);
    }

    public void deleteUser(final String id){
        repository.deleteById(id);
    }
}
