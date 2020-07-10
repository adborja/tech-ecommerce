package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
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
    public User getById(String id) {
        return User.builder()
                .id(id)
                .name("Pedro")
                .lastName("Navajas")
                .email("Eoeoeo@gmail.com")
                .build();
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public List<User> getByEmail(String email) {
        return Arrays.asList(
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Antonio")
                        .lastName("Alicate")
                        .email(email)
                        .build(),
                User.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Pedro")
                        .lastName("Navajas")
                        .email(email)
                        .build()
                );

    }

    @Override
    public User updateStore(String id, User user) {
        User found = getById(id);
        BeanUtils.copyProperties(user, found, Utils.getNullPropertyNames(user));
        return found;
    }
}
