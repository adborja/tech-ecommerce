package co.edu.cedesistemas.gateway.auth.service;

import co.edu.cedesistemas.gateway.auth.model.MongoUserDetails;
import co.edu.cedesistemas.gateway.auth.model.Role;
import co.edu.cedesistemas.gateway.auth.model.User;
import co.edu.cedesistemas.gateway.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);
        if (user == null || CollectionUtils.isEmpty(user.getRole())) {
            throw new UsernameNotFoundException("user not found");
        }

        String[] authorities = new String[user.getRole().size()];
        int count = 0;
        for (Role r : user.getRole()) {
            authorities[count++] = "ROLE_" + r.getRole();
        }
        return new MongoUserDetails(user.getEmail(), user.getPassword(), user.getActive(),
                user.isLocked(), user.isExpired(), user.isEnabled(), authorities);
    }

    public User getUserByEmail(final String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }
}
