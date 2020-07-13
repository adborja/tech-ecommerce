package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
                .name("fake user name")
                .lastName("fake user lastname")
                .email("fakemail@mail.com")
                .build();
    }

    @Override
    public List<User> getByMail(String mail) {
        return List.of(User.builder()
                .id(UUID.randomUUID().toString())
                .name("fake user name")
                .lastName("fake user lastname")
                .email(mail)
                .build());
    }

    @Override
    public void deleteUser(String id) {

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
