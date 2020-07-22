package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class UserServiceSandbox implements IUserService {

    @Override
    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        return user;
    }

    @Override
    public User updateUser(User user) {

        return user;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public Optional<User> getUser(String id) {
        User user = new User();
        user.setId(id);
        user.setLastName("prueba1");
        user.setName("cedesistemas");
        user.setEmail("cedepruebaœcede.com");
        return Optional.of(user);
    }

    @Override
    public Optional<User> getUserbyEmail(String email) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setLastName("prueba1");
        user.setName("cedesistemas");
        user.setEmail(email);
        return Optional.of(user);
    }
}
