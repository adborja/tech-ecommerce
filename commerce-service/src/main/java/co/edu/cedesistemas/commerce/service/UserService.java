package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> deleteUserById(String id) {
        Optional<User> foundUser = this.userRepository.findById(id);
        if (foundUser.isPresent()) {
            this.userRepository.delete(foundUser.get());
        }
        return foundUser;
    }

    @Override
    public Optional<User> updateUser(String id, User user) {
        Optional<User> userToUpdate = this.userRepository.findById(id);

        if (userToUpdate.isPresent()) {
            User foundUser = userToUpdate.get();
            foundUser.setName(user.getName());
            foundUser.setLastName(user.getLastName());
            return Optional.ofNullable(this.userRepository.save(foundUser));
        }
        return Optional.empty();
    }
}
