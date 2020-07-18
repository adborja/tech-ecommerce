package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Profile(SpringProfile.SANDBOX)
public class UserServiceSandbox implements IUserService {
    private final UserRepository repository;

    public User createUser(final User user) {
        user.setId(UUID.randomUUID().toString());
        return user;
    }

    public User getById(final String id) {
        final User user = new User();
        user.setId(id);
        user.setEmail("correo@correo.com");
        user.setName("jairo");
        user.setLastName("ortiz");
        return user;

    }

    public List<User> getByEmail(final String email) {
        User userSandbox = getById(UUID.randomUUID().toString());
        userSandbox.setEmail(email);
        return List.of(userSandbox);
    }

    public void deleteUserById (final String id){
        //do nothing
    }

    public User updateUser (final String id, final User User){
        User userSandbox = getById(id);
        BeanUtils.copyProperties(User,userSandbox, Utils.getNullPropertyNames(User));
        return userSandbox;
    }
}
