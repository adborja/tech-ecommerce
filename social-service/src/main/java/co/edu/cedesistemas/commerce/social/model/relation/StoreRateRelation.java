package co.edu.cedesistemas.commerce.social.model.relation;

import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.common.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Data
@Builder
@RelationshipEntity(type = "RATES")
public class StoreRateRelation {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private LocalDateTime rateTime;
    @Property
    private float rate;
    @JsonIgnore
    @StartNode
    private User user;
    @EndNode
    private Store store;
}
