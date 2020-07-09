package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    public Optional<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    public List<User> findUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> deleteUserById(String id) {
        Optional<User> foundUser = this.userRepository.findById(id);
        if (foundUser.isPresent()) {
            this.userRepository.delete(foundUser.get());
        }
        return foundUser;
    }

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
