package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.model.relation.FriendRelation;
import co.edu.cedesistemas.commerce.social.model.relation.ProductLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreRateRelation;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository.StoreOccurrence;
import co.edu.cedesistemas.commerce.social.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ProductService productService;
    private final StoreService storeService;

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        return repository.save(user);
    }

    public User createUser(String id) {
        User user = new User();
        user.setId(id);
        return repository.save(user);
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

    public void rateStore(final String userId, final String storeId, float value) throws Exception {
        User user = getById(userId);

        Store storeLiked = storeService.getById(storeId);

        if(storeLiked == null)
            throw new Exception("Store not found");

        user.storeRates(StoreRateRelation.builder()
                .user(user)
                .store(storeLiked)
                .rate(value)
                .build());
        update(user);
    }

    public void likeStore(final String userId, final String storeId) throws Exception {
        User user = getById(userId);
        Store storeLiked = storeService.getById(storeId);

        if(user == null)
            throw new Exception("user not found");

        if(storeLiked == null)
            throw new Exception("Store not found");

        user.storeLikes(StoreLikeRelation.builder()
                .user(user)
                .store(storeLiked)
                .storeLikeDate(LocalDateTime.now())
                .build());
        update(user);
    }

    public void addFriend(final String userId, final String friendId) throws Exception {
        User user = getById(userId);
        User newFriend = getById(friendId);

        if(user == null)
            throw new Exception("user not found");

        if(newFriend == null)
            throw new Exception("friend not found");

        user.addFriend(FriendRelation.builder()
                .user(user)
                .friend(newFriend)
                .friendshipTime(LocalDateTime.now())
                .build());
        update(user);
    }

    public void likeProduct(final String id, final String productId) throws Exception {
        User user = getById(id);
        Product productLiked = productService.getById(productId);

        if(user == null)
            throw new Exception("user not found");

        if (productLiked == null){
            throw new Exception("product not found");
        }

        user.likes(ProductLikeRelation.builder()
                .user(user)
                .product(productLiked)
                .likeTime(LocalDateTime.now())
                .build());
        update(user);

    }

    public List<StoreOccurrence> recommendStores(String id, String zone, String productType, Integer limit) {

        return storeService.recommendStoresByZoneAndProductType(id, zone, productType, limit);

    }
}