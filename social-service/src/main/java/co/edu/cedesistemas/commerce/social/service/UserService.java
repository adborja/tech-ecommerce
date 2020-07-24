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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
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

        User user = repository.findById(userId).orElse(null);
        if(user == null) {
            throw new Exception("Usuario no existe");
        }
        Store store = storeService.getById(storeId);
        if(store == null)
        {
            throw new Exception("No existe la tienda");
        }
        user.storeRates(StoreRateRelation.builder()
                .rate(value)
                .rateTime(LocalDateTime.now())
                .store(store)
                .user(user)
                .build());

    }

    public void likeStore(final String userId, final String storeId) throws Exception {
        User user = repository.findById(userId).orElse(null);
        if(user == null) {
            throw new Exception("Usuario no existe");
        }
        Store store = storeService.getById(storeId);
        if(store == null)
        {
            throw new Exception("No existe la tienda");
        }
        user.storeLikes(StoreLikeRelation.builder()
                .store(store)
                .user(user)
                .storeLikeDate(LocalDateTime.now())
                .build());

        repository.save(user);

    }

    public void addFriend(final String userId, final String friendId) throws Exception {
        User user = repository.findById(userId).orElse(null);
        User friend = repository.findById(friendId).orElse(null);

        if (user != null && friend != null) {
            user.addFriend(FriendRelation.builder()
                    .friend(friend)
                    .friendshipTime(LocalDateTime.now())
                    .user(user)
                    .build());
        } else {
            throw new Exception("No existe alguno de los usuarios");
        }
    }

    public void likeProduct(final String userId, final String productId) throws Exception {

        User user = repository.findById(userId).orElse(null);
        if(user == null) {
            throw new Exception("Usuario no existe");
        }
        Product product = productService.getById(productId);
        if(product == null)
        {
            throw new Exception("No existe la tienda");
        }

        user.likes(ProductLikeRelation.builder()
                .likeTime(LocalDateTime.now())
                .product(product)
                .user(user)
                .build());

        repository.save(user)

    }
}
