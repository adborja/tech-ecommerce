package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.interfaces.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class ProductServiceSandbox implements IProductService {
    @Override
    public Product getById(String id) {
        Product product = new Product();
        product.setDescription("This is a Fake Product 1");
        product.setId(id);
        product.setName("Fake Product 1");
        return product;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setDescription("This is a Fake Product 1");
        product.setId(UUID.randomUUID().toString());
        product.setName("Fake Product 1");
        products.add(product);
        return products;
    }

    @Override
    public Product updateProductById(String id) {
        Product product = new Product();
        product.setDescription("This is an Update of Fake Product 1");
        product.setId(id);
        product.setName("Fake Product 1");
        return product;
    }

    @Override
    public Product deleteProductById(String id) {
        Product product = new Product();
        product.setDescription("Fake Product Deleted");
        return product;
    }

    @Override
    public Product createProduct(Product product) {
        product.setDescription("This is a Fake Product 1");
        product.setId(UUID.randomUUID().toString());
        product.setName("Fake Product 1");
        return product;
    }
}
