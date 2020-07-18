package co.edu.cedesistemas.commerce.registration.commons;

import co.edu.cedesistemas.commerce.registration.model.User;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public final class TestUtils {
    public static User buildUser(final String email, final String name, final String lastName) {
        final User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastName);
        user.setBirthday(LocalDate.of(1990, 1, 1));
        user.setAddress(RandomString.make(15));
        user.setPassword(RandomString.make(8));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(UUID.randomUUID().toString());
        user.setStatus(User.Status.INACTIVE);
        return user;
    }
}