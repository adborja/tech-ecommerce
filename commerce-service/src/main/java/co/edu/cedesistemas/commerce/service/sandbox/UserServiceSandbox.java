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
        return user;
    }

    @Override
    public User getById(String id) {
        User user = new User();
        user.setId(id);
        user.setEmail("exam1@cedesistemas.com");
        user.setLastName("lastName 1");
        user.setName("Name 1");
        return user;
    }

    @Override
    public List<User> getByEmail(String email) {
        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setEmail("exam1@cedesistemas.com");
        user1.setLastName("lastName 1");
        user1.setName("Name 1");

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setEmail("exam2@cedesistemas.com");
        user2.setLastName("lastName 2");
        user2.setName("Name 2");

        return Arrays.asList(user1,user2);
    }

    @Override
    public User updateUser(String id, User user) throws Exception{
        if (user.getId()== null) {
            throw new Exception("id cannot be updated");
        }
        User found = getById(id);
        BeanUtils.copyProperties(user, found, Utils.getNullPropertyNames(user));
        return found;
    }

    @Override
    public void deleteUser(String id) { }
}
