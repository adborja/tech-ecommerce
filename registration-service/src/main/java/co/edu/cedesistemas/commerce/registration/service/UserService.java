package co.edu.cedesistemas.commerce.registration.service;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository repository;

    public User getUserById(String id){
        return repository.findUserById(id).orElse(null);
    }

    public User activateUserById(String id){
        User user = repository.findUserById(id).orElse(new User());
        log.debug("creando usuario: " + id);
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
