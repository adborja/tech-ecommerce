package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import org.apache.commons.lang3.RandomStringUtils;
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
        return createMockProduct();
    }

    @Override
    public List<Product> getProductByName(String name) {
        return Arrays.asList(createMockProduct(), createMockProduct());
    }

    @Override
    public Product updateProduct(Product productToUpdate, String productId) {
        productToUpdate.setDescription(productToUpdate.getDescription());
        return productToUpdate;
    }

    @Override
    public void deleteProduct(String productId) {

    }

    private Product createMockProduct(){
        return Product.builder()
                .id(UUID.randomUUID().toString())
                .name(RandomStringUtils.randomAlphabetic(10))
                .description(RandomStringUtils.randomAlphabetic(20))
                .build();
    }
}
