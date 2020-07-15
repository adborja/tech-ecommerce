package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class ProductServiceSandbox implements IProductService {
    @Override
    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        return product;
    }
    @Override
    public Product getProductById(String id) {
        return Product.builder()
                .description("Same Producto")
                .id(id)
                .name("p1").build();
    }

    @Override
    public Product deleteProductById(String id) {
        return Product.builder()
                .description("Same Producto")
                .id(id)
                .name("p1").build();
    }

    @Override
    public List<Product> getProductByName(String name) {
        Product p1 = Product.builder()
                .description("Some product")
                .id(UUID.randomUUID().toString())
                .name(name).build();
        Product p2 = Product.builder()
                .description("Other product")
                .id(UUID.randomUUID().toString())
                .name(name).build();
        return Arrays.asList(p1,p2);
    }

    @Override
    public Product updateProductById(String id, Product product) {
        product.setId(id);
        return product;
    }
}
