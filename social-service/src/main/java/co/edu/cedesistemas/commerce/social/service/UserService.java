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

import java.time.LocalDateTime;
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
        log.info("user created!! {} ",user);
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
            log.info("user found!! {}",user);
        }
        return user;
    }

    public void rateStore(final String userId, final String storeId, float value) throws Exception {
       User user =findUser(userId);
       Store store = finStore(storeId);

       if( user!=null && store!=null){
           user.storeRates(StoreRateRelation.builder().user(user).store(store).rate(value).rateTime(LocalDateTime.now()).build());
           repository.save(user);
       }
    }

    public void likeStore(final String userId, final String storeId) throws Exception {
        User user =findUser(userId);
        Store store = finStore(storeId);
        if( user!=null && store!=null){
            user.storeLikes(StoreLikeRelation.builder().store(store).user(user).build());
            repository.save(user);
        }
    }

    public void addFriend(final String userId, final String friendId) throws Exception {
        User friend = repository.findById(friendId).orElse(null);
        User user =repository.findById(userId).orElse(null);
        log.info("add friend from service  {} ",userId);
        if(user !=null && friend!=null){
            user.addFriend(FriendRelation.builder().user(user).friend(friend).friendshipTime(LocalDateTime.now()).build());
            repository.save(user);
        }
    }

    public void like(final String userId, final String productId) throws Exception{
        User user =findUser(userId);
        Product prd=productService.getById(productId);

        if(user !=null && prd!=null){
            user.likes(ProductLikeRelation.builder().product(prd).user(user).likeTime(LocalDateTime.now()).build());
        }
        repository.save(user);

    }

    private User findUser(final String userId ){
        return repository.findById(userId).orElse(null);
    }

    private Store finStore(final String storeId){
        return storeService.getById(storeId);
    }
}
