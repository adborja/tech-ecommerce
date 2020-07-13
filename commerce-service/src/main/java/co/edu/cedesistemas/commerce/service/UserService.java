package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    @Override
    public User create(User user){
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id){
        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElse(null);
    }

    @Override
    public User updateUser(User userToUpdate, String id){
        return userRepository.findById(id)
                .map(actualUser -> updateUser(userToUpdate, actualUser))
                .map(userRepository::save)
                .orElse(null);
    }

    private User updateUser(User userToUpdate, User actualUser){
        actualUser.setName(userToUpdate.getName());
        actualUser.setLastName(userToUpdate.getLastName());

        return actualUser;
    }

    @Override
    public void deleteUser(String userId){
        userRepository.findById(userId)
                .ifPresent(userRepository::delete);
    }
}
