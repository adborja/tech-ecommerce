package co.edu.cedesistemas.commerce.loyalty.service;

import co.edu.cedesistemas.commerce.loyalty.model.UserStore;
import co.edu.cedesistemas.commerce.loyalty.repository.UserStoreRepository;
import co.edu.cedesistemas.common.model.LoyaltyStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserStoreService {
    private final UserStoreRepository repository;
    private final EventPublisherService publisherService;

    public UserStore createUserStore(final UserStore userStore) {
        userStore.setId(UUID.randomUUID().toString());
        userStore.setCreatedAt(LocalDateTime.now());
        userStore.setStatus(LoyaltyStatus.USER_CREATED);
        userStore.setPoints(userStore.getPoints());

        UserStore created = repository.save(userStore);
        
        publisherService.publishLoyaltyEvent(created);

        return created;
    }

    public UserStore updatePoints(final String storeId, final String userId, final int points) {
        UserStore userStore = getByStoreIdAndUserId(storeId, userId);
        if (userStore == null) {
            return null;
        }
        userStore.setPoints(userStore.getPoints() + points);
        return repository.save(userStore);
    }

    public UserStore getByStoreIdAndUserId(final String storeId, final String userId) {
        return repository.findByStoreIdAndUserId(storeId, userId).orElse(null);
    }

    public List<UserStore> getUsers(final String storeId) {
        return repository.findByStoreId(storeId);
    }

    public void deleteUserStore(final String id) {
        repository.deleteById(id);
    }
}
