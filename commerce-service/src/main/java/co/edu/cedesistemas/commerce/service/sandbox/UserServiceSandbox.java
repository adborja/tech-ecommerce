package co.edu.cedesistemas.commerce.service.sandbox;

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
        user.setName("");
        user.setLastName("");
        user.setEmail("algo@prueba.com");
        return user;
    }

    @Override
    public User getById(String id) {
        User user = new User();
        user.setId(id);
        user.setName("");
        user.setLastName("");
        user.setEmail("algo@prueba.com");
        return user;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public List<User> getByEmail(String email) {
        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setName("");
        user1.setLastName("");
        user1.setEmail("algo@prueba.com");

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setName("");
        user2.setLastName("");
        user2.setEmail("algo@prueba.com");

        return Arrays.asList(user1, user2);
    }

    @Override
    public User updateUser(String id, User user) throws Exception {
         if (user.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        User found = getById(id);
        BeanUtils.copyProperties(user, found, Utils.getNullPropertyNames(user));
        return found;
    }
}
