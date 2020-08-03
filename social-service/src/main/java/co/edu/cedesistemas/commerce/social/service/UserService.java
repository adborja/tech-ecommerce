package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.model.relation.FriendRelation;
import co.edu.cedesistemas.commerce.social.model.relation.ProductLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreRateRelation;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.repository.UserRepository;
import co.edu.cedesistemas.common.event.SocialEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ProductService productService;
    private final StoreService storeService;
    private final EventPublisherService publisherService;

    public User createUser(String id) {
        User user = new User();
        user.setId(id);
        User created = repository.save(user);
        publisherService.publishSocialUserEvent(created, SocialEvent.Status.CREATED);
        return created;
    }

    public User update(User user) {
        return repository.save(user);
    }

    public User getById(final String id) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            Set<Product> productsLiked = productService.getByUserLiked(user.getId());
            user.set_liked(productsLiked.stream().map(Product::getId).collect(Collectors.toSet()));

            Set<Store> storesLiked = storeService.getByUserLiked(user.getId());
            user.set_storesLiked(storesLiked.stream().map(Store::getId).collect(Collectors.toSet()));

            Set<User> friends = repository.findFriendsByUser(user.getId());
            User.UserFriendResult result = User.UserFriendResult.builder()
                    ._hints(friends.size())
                    .friends(friends.stream().map(User::getId).collect(Collectors.toSet()))
                    .build();
            user.set_friends(result);
        }
        return user;
    }

    public void deleteUser(final String id) {
        User found = getById(id);
        if (found != null) {
            repository.deleteById(id);
            publisherService.publishSocialUserEvent(found, SocialEvent.Status.DELETED);
        }
    }

    public void rateStore(final String userId, final String storeId, float value) throws Exception {
        User user = repository.findById(userId).orElse(null);
        Store store = storeService.getById(storeId);
        if (user != null && store != null) {
            user.storeRates(StoreRateRelation.builder()
                    .rate(value)
                    .rateTime(LocalDateTime.now())
                    .store(store)
                    .user(user)
                    .build());
            repository.save(user);
        } else {
            throw new Exception("user or store not found");
        }

    }

    public void likeStore(final String userId, final String storeId) throws Exception {
        User user = repository.findById(userId).orElse(null);
        Store store = storeService.getById(storeId);
        if (user != null && store != null) {
            user.storeLikes(StoreLikeRelation.builder()
                    .storeLikeDate(LocalDateTime.now())
                    .store(store)
                    .user(user)
                    .build());
            repository.save(user);
        } else {
            throw new Exception("user or store not found");
        }
    }

    public void likeProduct(final String userId, final String productId) throws Exception {
        User user = repository.findById(userId).orElse(null);
        Product product = productService.getById(productId);
        if (user != null && product != null) {
            user.likes(ProductLikeRelation.builder()
                    .likeTime(LocalDateTime.now())
                    .user(user)
                    .product(product)
                    .build());
            repository.save(user);
        } else {
            throw new Exception("user or product not found");
        }
    }

    public void addFriend(final String userId, final String friendId) throws Exception {
        User user = repository.findById(userId).orElse(null);
        User friend = repository.findById(friendId).orElse(null);
        if (user != null && friend != null) {
            user.addFriend(FriendRelation.builder()
                    .friendshipTime(LocalDateTime.now())
                    .user(user)
                    .friend(friend)
                    .build());
            repository.save(user);
        } else {
            throw new Exception("user or friend not found");
        }
    }

    public List<StoreRepository.StoreOccurrence> findRecommendationByStore(final String userId, final String zone,
                                                                           final String productType,
                                                                           final Integer limit) throws Exception {

        return storeService.recommendStoresByZoneAndProductType(userId,zone,productType,limit);
    }
}
