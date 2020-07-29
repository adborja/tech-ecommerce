package co.edu.cedesistemas.gateway.auth.service;

import co.edu.cedesistemas.gateway.auth.model.User;

public interface ILoginService {
    String login(String username, String password);
    User saveUser(User user);
    boolean logout(String token);
    Boolean isValidToken(String token);
    String createNewToken(String token);
}