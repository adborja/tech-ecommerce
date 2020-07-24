package co.edu.cedesistemas.commerce.registration.services;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        user.setStatus(User.Status.INACTIVE);
        return userRepository.save(user);
    }

    public User activateUser(String userId){
        return userRepository.findById(userId)
                .map(user -> {
                    user.setStatus(User.Status.ACTIVE);
                    return userRepository.save(user);
                }).orElse(null);

    }

    public User getUserById(String userId){
        return userRepository.findById(userId).orElse(null);
    }
}
