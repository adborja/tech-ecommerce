package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public User getUserById(String id){
        return repository.findUserById(id).orElse(null);
    }

    public User activateUserById(String id){
        User user = repository.findUserById(id).orElse(new User());
        if (user.getId() != null) {
            user.setStatus(User.Status.ACTIVE);
            user = repository.save(user);
        }
        return user;
    }

    public User createUser(User user){
        return repository.save(user);
    }

}
