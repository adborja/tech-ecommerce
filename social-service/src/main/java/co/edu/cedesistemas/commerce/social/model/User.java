package co.edu.cedesistemas.commerce.social.model;

import co.edu.cedesistemas.commerce.social.model.relation.FriendRelation;
import co.edu.cedesistemas.commerce.social.model.relation.ProductLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.PurchaseRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreLikeRelation;
import co.edu.cedesistemas.commerce.social.model.relation.StoreRateRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@NodeEntity
public class User {
    @Id
    private String id;

    @JsonIgnore
    @Relationship(type = "PURCHASES", direction = Relationship.UNDIRECTED)
    private Set<PurchaseRelation> purchased = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "LIKES", direction = Relationship.UNDIRECTED)
    private Set<ProductLikeRelation> liked = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "LIKES", direction = Relationship.UNDIRECTED)
    private Set<StoreLikeRelation> storesLiked = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "IS_FRIEND_OF", direction = Relationship.UNDIRECTED)
    private Set<FriendRelation> friends = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "RATES", direction = Relationship.UNDIRECTED)
    private Set<StoreRateRelation> storesRated = new HashSet<>();

    @Transient
    private Set<String> _liked;
    @Transient
    private Set<String> _storesLiked;
    @Transient
    private UserFriendResult _friends;

    public void purchases(PurchaseRelation product) {
        if (purchased == null) {
            purchased = new HashSet<>();
        }
        purchased.add(product);
    }

    public void purchases(Set<PurchaseRelation> products) {
        if (purchased == null) {
            purchased = new HashSet<>();
        }
        purchased.addAll(products);
    }

    public void likes(ProductLikeRelation product) {
        if (liked == null) {
            liked = new HashSet<>();
        }
        liked.add(product);
    }

    public void storeLikes(StoreLikeRelation store) {
        if (storesLiked == null) {
            storesLiked = new HashSet<>();
        }
        storesLiked.add(store);
    }

    public void storeRates(StoreRateRelation rate) {
        if (storesRated == null) {
            storesRated = new HashSet<>();
        }
        storesRated.add(rate);
    }

    public void addFriend(FriendRelation friend) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(friend);
    }



    @Data
    @Builder
    public static class UserFriendResult {
        private int _hints;
        private Set<String> friends;
    }
}
