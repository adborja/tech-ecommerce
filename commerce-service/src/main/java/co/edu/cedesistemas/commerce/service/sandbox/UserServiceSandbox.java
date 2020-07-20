package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.interfaces.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class UserServiceSandbox implements IUserService {
    @Override
    public User getById(String id) {
        User user = new User();
        user.setEmail("fakeuser@fakemail.com");
        user.setId(id);
        user.setLastName("Fake LastName");
        user.setName("Fake Name");
        return user;
    }

    @Override
    public List<User> getByEmail(String email) {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setEmail("fakeuser@fakemail.com");
        user.setId(UUID.randomUUID().toString());
        user.setLastName("Fake LastName");
        user.setName("Fake Name");
        users.add(user);
        return users;
    }

    @Override
    public User updateUser(String id, User user) {
        user.setName("fakeUser updated");
        user.setLastName("Fake LastName");
        user.setId(id);
        user.setEmail("fakeUpdated@fakeemail.com");
        return user;
    }

    @Override
    public User deleteUser(String id) {
        User user = new User();
        user.setName("fakeUser deleted");
        return user;
    }

    @Override
    public User createUser(User user) {
        user.setEmail("fakeuserCreated@fakemail.com");
        user.setId(UUID.randomUUID().toString());
        user.setLastName("Fake LastName Created");
        user.setName("Fake Name Created");
        return user;
    }
}
