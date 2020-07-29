package co.edu.cedesistemas.gateway.auth.service;

import co.edu.cedesistemas.gateway.auth.model.User;
import co.edu.cedesistemas.gateway.auth.repository.JwtTokenRepository;
import co.edu.cedesistemas.gateway.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoginService implements ILoginService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final JwtTokenRepository jwtTokenRepository;

    @Override
    public String login(final String username, final String password) {
        try {
            User user = userService.getUserByEmail(username);

            if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
                throw new RuntimeException("invalid username or password");
            }

            return jwtTokenProvider.createToken(username, user.getRole().stream()
                    .map(r -> "ROLE_" + r.getRole()).collect(Collectors.toList()));
        } catch (AuthenticationException ex) {
            throw new RuntimeException("invalid username or password");
        }
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.saveUser(user);
    }

    @Override
    public boolean logout(String token) {
        jwtTokenRepository.deleteById(token);
        return true;
    }

    @Override
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String> roleList = jwtTokenProvider.getRoleList(token);
        return jwtTokenProvider.createToken(username, roleList);
    }
}
