package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.model.relation.FriendRelation;
import co.edu.cedesistemas.commerce.social.model.relation.ProductLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreRateRelation;
import co.edu.cedesistemas.commerce.social.repository.ProductRepository;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ProductService productService;
    private final StoreService storeService;

    public User createUser(String id) {
        User user = new User();
        user.setId(id);
        return repository.save(user);
    }

    public List<StoreRepository.StoreOccurrence> recomendations (String id,
                                                                 String zone,
                                                                 String productType,
                                                                 Integer limit){
        return storeService.recommendStoresByZoneAndProductType(id,zone,productType,limit);
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
        User user = repository.findById(userId).get();
        Store store = storeService.getById(storeId);
        user.storeRates(StoreRateRelation.builder()
                .store(store)
                .rate(value)
                .user(user).build());
        repository.save(user);
    }

    public User likeStore(final String userId, final String storeId) throws Exception {
        User user = repository.findById(userId).get();
        Store store = storeService.getById(storeId);
        user.storeLikes(StoreLikeRelation.builder()
                .store(store)
                .storeLikeDate(LocalDateTime.now())
                .user(user).build());
        repository.save(user);
        return user;
    }

    public User likeProduct(final String userId, final String productId) throws Exception {
        User user = repository.findById(userId).get();
        Product product = productService.getById(productId);
        ProductLikeRelation relation = ProductLikeRelation.builder()
                .id(new Random().nextLong())
                .likeTime(LocalDateTime.now())
                .user(user)
                .product(product).build();
        user.likes(relation);
        repository.save(user);
        return user;
    }

    public void addFriend(final String userId, final String friendId) throws Exception {
        User user = repository.findById(userId).get();
        User userFriend = repository.findById(userId).get();
        user.addFriend(FriendRelation.builder()
                .friend(userFriend)
                .friendshipTime(LocalDateTime.now())
                .build());
        repository.save(user);
    }
}
