package co.edu.cedesistemas.commerce.service;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@XSlf4j
@Service
//@AllArgsConstructor
@Profile("!" + SpringProfile.SANDBOX)
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

//    public UserService (UserRepository repository){
  //      this.repository = repository;
    //}

    public User createUser(final User user) {
        user.setId(UUID.randomUUID().toString());
        return repository.save(user);
    }

    public User getById(final String id) {
        Optional<User>  userId = repository.findById(id);
        if (userId.isPresent()) {
            return userId.get();
        }
        else { return null;}

    }

    public List<User> getByEmail(final String mail) {
        List<User> userId = repository.findByEmail(mail);
        if (userId.isEmpty()) {
            return userId;
        }
        else { return null;}

    }


    public List<User> getByName(final String name) {
        List<User>  userName =   repository.findByNameLike(name);
        return userName;
    }

    public User updateUser(String id, User user)
    {
        Optional<User> userUpdate = repository.findById(id);
        if (userUpdate.isPresent())
        {
            return repository.save(user);
        }
        else {return null;}

    }

    public void deleteUser(final String id) {
        repository.deleteById(id);
    }

}
