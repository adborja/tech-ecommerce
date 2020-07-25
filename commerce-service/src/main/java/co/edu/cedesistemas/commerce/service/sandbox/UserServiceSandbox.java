package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .email("fake email@gmail.com")
                .lastName("fake last name")
                .name("fake name")
                .build();
    }
    @Override
    public User updateUserById(String id, User user) {
        user.setId(id);
        return user;
    }

    @Override
    public List<User> getUserByEmail(String email) {
        User user1 =  User.builder()
                .id(UUID.randomUUID().toString())
                .email("fakeemail@gmailcom")
                .lastName("fake lastname")
                .name("fake name")
                .build();
        User user2 =  User.builder()
                .id(UUID.randomUUID().toString())
                .email("fakeemail2@gmailcom")
                .lastName("fake lastname2")
                .name("fake name2")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        return users;
    }

    @Override
    public User getUserById(String id) {
        return User.builder()
                .id(id)
                .email("fakeemail@gmail.com")
                .lastName("fake lastname")
                .name("fake name")
                .build();
    }
}
