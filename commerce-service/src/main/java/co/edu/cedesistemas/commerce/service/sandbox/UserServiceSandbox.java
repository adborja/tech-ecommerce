package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
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
    public Optional<User> findById(String id) {
        User user = new User();
        user.setId(id);
        user.setName("Prueba sandbox");
        user.setLastName("sandbox");
        user.setEmail("prueba@gmail.com");
        return Optional.of(user);
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setName("Prueba sandbox");
        user1.setLastName("sandbox");
        user1.setEmail("prueba@gmail.com");
        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setName("Prueba sandbox 2");
        user2.setLastName("sandbox 2");
        user2.setEmail("prueba2@gmail.com");
        List<User> users = List.of(user1, user2);
        return users;
    }

    @Override
    public Optional<User> deleteUserById(String id) {
        Optional<User> found = findById(id);

        if (found.isPresent()) {
            return found;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUser(String id, User user) throws Exception {

        if (StringUtils.isEmpty(user.getId())) {
            throw new Exception("User couldn't update");
        }
        Optional<User> found = findById(id);
        BeanUtils.copyProperties(user, found.get(), Utils.getNullPropertyNames(user));
        return found;
    }
}
