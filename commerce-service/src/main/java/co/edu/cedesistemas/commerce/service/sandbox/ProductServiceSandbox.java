package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Profile(SpringProfile.SANDBOX)
public class ProductServiceSandbox implements IProductService {
    private final ProductRepository repository;

    public Product createProduct(final Product product) {
        product.setId(UUID.randomUUID().toString());
        return product;
    }

    public Product getById(final String id) {
        Product productSandbox = new Product();
        productSandbox.setId(UUID.randomUUID().toString());
        productSandbox.setName("Product name");
        productSandbox.setDescription("Product Description");
        return productSandbox;
    }

    public List<Product> getByName(final String name) {
        Product productSandbox = getById(UUID.randomUUID().toString());
        productSandbox.setName(name);
        return List.of(productSandbox);
    }

    public void deleteProductById (final String id){
        // do nothing
    }

    public Product updateProduct (final String id, final Product product){
        Product productSandbox = getById(id);
        BeanUtils.copyProperties(product,productSandbox, Utils.getNullPropertyNames(product));
        return productSandbox;
    }
}
