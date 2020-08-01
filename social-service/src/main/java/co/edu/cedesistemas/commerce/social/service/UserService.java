package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.model.relation.FriendRelation;
import co.edu.cedesistemas.commerce.social.model.relation.ProductLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreRateRelation;
import co.edu.cedesistemas.commerce.social.repository.UserRepository;
import co.edu.cedesistemas.common.event.SocialEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
        StoreRateRelation storeRateRelation = StoreRateRelation.builder().build();
        storeRateRelation.setUser(user);
        storeRateRelation.setStore(store);
        user.storeRates(storeRateRelation);
        repository.save(user);

    }

    public void likeStore(final String userId, final String storeId) throws Exception {
        User user = repository.findById(userId).orElse(null);
        Store store = storeService.getById(storeId);
        StoreLikeRelation storeLikeRelation = StoreLikeRelation.builder().build();
        storeLikeRelation.setUser(user);
        storeLikeRelation.setStore(store);
        user.storeLikes(storeLikeRelation);
        repository.save(user);
    }

    public void addFriend(final String userId, final String friendId) throws Exception {
        User user = repository.findById(userId).orElse(null);
        User friend = repository.findById(userId).orElse(null);
        Set<User> friends = new HashSet<>();
        friends.add(user);
        friends.add(friend);
        FriendRelation friendRelation = FriendRelation.builder().build();
        friend.addFriend(friendRelation);
        repository.save(user);
    }
    public void like(final String userId, final String productId) throws Exception {
        User user = repository.findById(userId).orElse(null);
        Product product = productService.getById(productId);
        ProductLikeRelation productLikeRelation = ProductLikeRelation.builder().build();
        productLikeRelation.setProduct(product);
        productLikeRelation.setUser(user);
        user.likes(ProductLikeRelation.builder()
                .user(user)
                .product(product)
                .build());
        update(user);

        }

}
