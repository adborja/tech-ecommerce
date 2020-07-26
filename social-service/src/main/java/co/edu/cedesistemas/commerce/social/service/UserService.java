package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.model.relation.FriendRelation;
import co.edu.cedesistemas.commerce.social.model.relation.ProductLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreRateRelation;
import co.edu.cedesistemas.commerce.social.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final ProductService productService;
    private final StoreService storeService;

    public User createUser(String id) {
        User user = new User();
        user.setId(id);
        return repository.save(user);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public User getById(final String id) {
        User user = repository.findUser(id);
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
        User user = repository.findUser(userId);
        Store store = storeService.getById(storeId);
        if(user == null || store == null){
            log.warn("User or store not found, not was possible rate store");
            throw new Exception("User or store not found, not was possible rate store");
        }
        StoreRateRelation rate = StoreRateRelation.builder().rate(value).user(user).store(store).build();
        user.storeRates(rate);
        repository.save(user);
    }

    public void likeStore(final String userId, final String storeId) throws Exception {
        User user = repository.findUser(userId);
        Store store = storeService.getById(storeId);
        if(user == null || store == null){
            log.warn("User or store not found, not was possible like store");
            throw new Exception("User or store not found, not was possible like store");
        }
        StoreLikeRelation like = StoreLikeRelation.builder().user(user).store(store).build();
        user.storeLikes(like);
        repository.save(user);
    }

    public void likeProduct(final String userId, final String productId) throws Exception {
        User user = repository.findUser(userId);
        Product product = productService.getById(productId);
        if(user == null || product == null){
            log.warn("User or product not found, not was possible like product");
            throw new Exception("User or product not found, not was possible like product");
        }
        ProductLikeRelation like = ProductLikeRelation.builder().user(user).product(product).build();
        user.likes(like);
        repository.save(user);
    }

    public void addFriend(final String userId, final String friendId) throws Exception {
        User user = repository.findUser(userId);
        User friendUser = repository.findUser(friendId);
        if(user == null){
            log.warn("User not found, not was possible add a friend");
            throw new Exception("User not found, not was possible add a friend");
        }
        FriendRelation relation = FriendRelation.builder().user(user).friend(friendUser).build();
        user.addFriend(relation);
        repository.save(user);
    }


}
