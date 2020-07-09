package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user){
        return userRepository.save(user);
    }

    public User getUserById(String id){
        return userRepository.findById(id)
                .orElse(null);
    }

    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElse(null);
    }

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

    public void deleteUser(String userId){
        userRepository.findById(userId)
                .ifPresent(userRepository::delete);
    }
}
