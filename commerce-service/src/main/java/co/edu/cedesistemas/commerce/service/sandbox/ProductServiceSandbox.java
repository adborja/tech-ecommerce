package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .description("fake description")
                .id(id)
                .name("producto1").build();
    }

    @Override
    public Product deleteProductById(String id) {
        return Product.builder()
                .description("fake description delete")
                .id(id)
                .name("producto1").build();
    }

    @Override
    public List<Product> getProductByName(String name) {
        Product product1 = Product.builder()
                .description("prodcu1 description")
                .id(UUID.randomUUID().toString())
                .name(name).build();
        List<Product> products = new ArrayList<>();
     products.add(product1);
        return products;
    }

    @Override
    public Product updateProductById(String id, Product product) {
        product.setId(id);
        return product;
    }
}
