package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
    public User deleteUserById(String id) {
        return User.builder()
                .id(id)
                .email("correofalso@falso.com")
                .lastName("Falsa")
                .name("Persona")
                .build();
    }

    @Override
    public User updateUserById(String id, User user) {
        user.setId(id);
        return user;
    }

    @Override
    public List<User> getUserByEmail(String email) {
        return Arrays.asList(
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .email("correofalso@falso.com")
                        .lastName("Falsa")
                        .name("Persona")
                        .build(),
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .email("correomasfalso@falso.com")
                        .lastName("Falsa")
                        .name("Otro")
                        .build()
        );
    }

    @Override
    public User getUserById(String id) {
        return User.builder()
                .id(id)
                .email("correofalso@falso.com")
                .lastName("Falsa")
                .name("Persona")
                .build();
    }
}
