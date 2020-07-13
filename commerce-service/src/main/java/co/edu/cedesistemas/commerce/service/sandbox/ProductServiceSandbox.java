package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
    public Product getById(String id) {
        return Product.builder()
                .id(id)
                .name("fake product name")
                .description("fake product description")
                .build();
    }

    @Override
    public List<Product> getByName(String name) {
        return List.of(Product.builder()
                .id(UUID.randomUUID().toString())
                .name("name")
                .description("fake product description")
                .build());
    }

    @Override
    public void deleteProduct(String id) {

    }

    @Override
    public Product updateProduct(String id, Product product) throws Exception {
        if (product.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        Product found = getById(id);
        BeanUtils.copyProperties(product, found, Utils.getNullPropertyNames(product));
        return found;
    }
}
