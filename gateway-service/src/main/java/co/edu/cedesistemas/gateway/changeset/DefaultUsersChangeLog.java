package co.edu.cedesistemas.gateway.changeset;

import co.edu.cedesistemas.gateway.auth.model.Role;
import co.edu.cedesistemas.gateway.auth.model.User;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import io.changock.migration.api.annotations.ChangeLog;
import io.changock.migration.api.annotations.ChangeSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.UUID;

@ChangeLog(order = "001")
@Slf4j
public class DefaultUsersChangeLog {
    @ChangeSet(order = "001", id = "add_default_users", author = "adborja")
    public void addDefaultUsers(final MongockTemplate template, final PasswordEncoder passwordEncoder) {
        log.info("adding default users");
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode("password"));
        user.setActive(1);
        user.setLocked(false);
        user.setEmail("user@company.com");
        user.setEnabled(true);
        user.setExpired(false);
        user.setRole(Collections.singleton(Role.builder().id(1).role("ADMIN").build()));
        user.setName("juan");
        user.setLastName("perez");
        template.save(user);
    }
}
