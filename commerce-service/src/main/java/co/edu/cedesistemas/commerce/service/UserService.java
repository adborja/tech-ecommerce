package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.commerce.service.interfaces.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Slf4j
@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository repository;

    public User getById(final String id) {
        return repository.getById(id).orElse(null);
    }

    public List<User> getByEmail(String email){
        return repository.getByEmail(email);
    }

    public User updateUser(String id, User user){
        User found = getById(id);
        if (found == null) return null;
        BeanUtils.copyProperties(user, found, Utils.getNullPropertyNames(user));
        return repository.save(found);
    }

    public User deleteUser(String id){
        try{
            repository.deleteById(id);
        }catch (Exception e){
            log.debug(e.getMessage());
        }finally {
            return new User();
        }
    }

    public User createUser(User user){
        user.setId(UUID.randomUUID().toString());
        return repository.save(user);}
}
