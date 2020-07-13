package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public User getById(final String id) {
        return null;
    }

    public List<User> getByEmail(String name){
        return null;
    }

    public User updateUser(String id, User user){return null;}

    public User deleteUser(String id){return null;}

    public User createUser(User user){return repository.save(user);}
}
