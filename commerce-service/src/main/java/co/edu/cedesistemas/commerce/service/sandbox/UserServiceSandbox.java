package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.SpringProfile;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class UserServiceSandbox implements IUserService {

    @Override
    public User create(User user) {
        user.setId(UUID.randomUUID().toString());
        return user;
    }

    @Override
    public User getUserById(String id) {
        return createMockUser();
    }

    @Override
    public User getUserByEmail(String email) {
        return createMockUser();
    }

    @Override
    public User updateUser(User userToUpdate, String id) {
        return userToUpdate;
    }

    @Override
    public void deleteUser(String userId) {

    }

    private User createMockUser(){
        return User.builder()
                .id(UUID.randomUUID().toString())
                .name(RandomStringUtils.randomAlphabetic(10))
                .lastName(RandomStringUtils.randomAlphabetic(10))
                .email(RandomStringUtils.randomAlphabetic(5) + "@company.com")
                .build();
    }
}
