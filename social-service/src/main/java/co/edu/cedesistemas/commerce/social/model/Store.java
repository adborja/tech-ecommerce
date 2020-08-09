package co.edu.cedesistemas.commerce.social.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NodeEntity
public class Store extends RepresentationModel<Store> implements Serializable {
    @Id
    private String id;

    private Set<String> productTypeNames;

    @Relationship(type = "HAS", direction = Relationship.UNDIRECTED)
    private Set<Product> products;

    @Relationship(type = "LOCATED_IN", direction = Relationship.UNDIRECTED)
    private Location location;

    @Relationship(type = "SERVES", direction = Relationship.UNDIRECTED)
    private Set<ProductType> productTypes;

    public void has(Product product) {
        if (products == null) {
            products = new HashSet<>();
        }
        products.add(product);
    }

    public void has(Set<Product> _products) {
        if (products == null) {
            products = new HashSet<>();
        }
        products.addAll(_products);
    }

    public void addProductType(ProductType productType) {
        if (productTypes == null) {
            productTypes = new HashSet<>();
        }
        productTypes.add(productType);
    }
}
