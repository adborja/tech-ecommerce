package co.edu.cedesistemas.commerce.service;


import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    public Optional<User> getById(String id) {
        return repository.findById(id);
    }

    public List<User> getByMail(String mail){ return repository.findByEmail(mail);}

    public void  deleteUser(String id){  repository.deleteById(id);}

    public User updateUser(String id, User user) {

        User userToSave = repository.findById(id).get();
        String[] ignoreA = Utils.getNullPropertyNames(user);
        BeanUtils.copyProperties(user,userToSave, Utils.getNullPropertyNames(user));
        return repository.save(userToSave);

    }
}
