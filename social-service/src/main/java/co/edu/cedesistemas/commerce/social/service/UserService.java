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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProductService productService;
    private final StoreService storeService;

    public User createUser(String id) {
        User user = new User();
        user.setId(id);
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User getById(final String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            Set<Product> productsLiked = productService.getByUserLiked(user.getId());
            user.set_liked(productsLiked.stream().map(Product::getId).collect(Collectors.toSet()));

            Set<Store> storesLiked = storeService.getByUserLiked(user.getId());
            user.set_storesLiked(storesLiked.stream().map(Store::getId).collect(Collectors.toSet()));

            Set<User> friends = userRepository.findFriendsByUser(user.getId());
            User.UserFriendResult result = User.UserFriendResult.builder()
                    ._hints(friends.size())
                    .friends(friends.stream().map(User::getId).collect(Collectors.toSet()))
                    .build();
            user.set_friends(result);
        }
        return user;
    }

    public void rateStore(final String userId, final String storeId, float value) throws Exception {
        User userFound = userRepository.findById(userId).orElse(null);
        if(userFound == null){
            throw new Exception("user not found");
        }
        Store storeToRate = storeService.getStoreById(storeId);
        if(storeToRate == null){
            throw new Exception("store not found");
        }

        userFound.storeRates(StoreRateRelation.builder()
                .rateTime(LocalDateTime.now())
                .rate(value)
                .user(userFound)
                .store(storeToRate)
                .build());
        update(userFound);

    }

    public void likeStore(final String userId, final String storeId) throws Exception {
        User user = getById(userId);
        Store store = storeService.getStoreById(storeId);

        if(store == null){
            throw new Exception("store not found");
        }

        user.storeLikes(StoreLikeRelation.builder()
            .user(user)
            .store(store)
            .build());
        update(user);
    }

    public void likeProduct(final String userId, final String productId) throws Exception{
        User user = getById(userId);
        Product productLiked = productService.getById(productId);

        if (productLiked == null){
            throw new Exception("product not found");
        }

        user.likes(ProductLikeRelation.builder()
                .user(user)
                .product(productLiked)
                .build());
        update(user);
    }

    public void addFriend(final String userId, final String friendId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isEmpty()){
            throw new Exception("User not found");
        }
        Optional<User> userFriend = userRepository.findById(friendId);
        if(!userFriend.isEmpty()){
            throw new Exception("Friend not found");
        }

        user.get().addFriend(FriendRelation.builder()
                .user(user.get())
                .friend(userFriend.get())
                .friendshipTime(LocalDateTime.now())
                .build());
        userRepository.save(user.get());
    }
}
