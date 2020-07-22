package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class ProductServiceSandbox implements IProductService {


    @Override
    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return product;
    }

    @Override
    public void deleteProduct(Product product) {

    }

    @Override
    public Optional<Product> getProduct(String id) {
        Product product = new Product();
        product.setId(id);
        product.setDescription("producto prueba");
        product.setName("producto1");
        return Optional.of(product);
    }

    @Override
    public List<Product> getProductbyName(String name) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setDescription("producto prueba");
        product.setName(name);
        List<Product> products = new ArrayList<>();
        products.add(product);
        return products;
    }
}
