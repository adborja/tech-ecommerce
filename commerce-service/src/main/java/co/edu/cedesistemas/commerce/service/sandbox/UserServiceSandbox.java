package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        user.setEmail("unemail@gmail.com");
        user.setLastName("Lopez");
        user.setName("Manuela");
        return user;
    }

    @Override
    public List<User> getByEmail(String email) {
        List<User> list = new ArrayList<>();
        User user = getById(UUID.randomUUID().toString());
        user.setEmail(email);
        list.add(user);
        return list;
    }

    @Override
    public User updateUser(String id, User user) throws Exception {
        if(user.getId() != null){
            throw new Exception("id can not be updated");
        }
        User found = getById(id);
        BeanUtils.copyProperties(user, found, Utils.getNullPropertyNames(user));
        return found;
    }

    @Override
    public void deleteUser(String id) {
        return;
    }
}
