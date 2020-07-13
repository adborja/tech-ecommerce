package co.edu.cedesistemas.commerce.social.model.relation;

import co.edu.cedesistemas.commerce.social.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.time.LocalDateTime;

@Data
@Builder
@RelationshipEntity(type = "IS_FRIEND_OF")
public class FriendRelation {
    @Id @GeneratedValue
    private Long id;
    @Property
    private LocalDateTime friendshipTime;
    @JsonIgnore
    @StartNode
    private User user;
    @JsonIgnore @EndNode
    private User friend;
}
