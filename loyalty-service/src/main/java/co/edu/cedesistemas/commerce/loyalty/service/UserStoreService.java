package co.edu.cedesistemas.commerce.loyalty.service;

import co.edu.cedesistemas.commerce.loyalty.model.UserStore;
import co.edu.cedesistemas.commerce.loyalty.repository.UserStoreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserStoreService {
    private final UserStoreRepository repository;

    public UserStore createUserStore(final UserStore userStore) {
        userStore.setId(UUID.randomUUID().toString());
        userStore.setCreatedAt(LocalDateTime.now());
        userStore.setStatus(UserStore.Status.ACTIVE);
        return repository.save(userStore);
    }
}
