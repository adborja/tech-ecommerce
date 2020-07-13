package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
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
    public List<Product> getProductByNamePrefix(String name) {
        Product product1 = new Product();
        product1.setId(UUID.randomUUID().toString());
        product1.setName(name);
        product1.setDescription("Descripcion1");
        Product product2 = new Product();
        product2.setId(UUID.randomUUID().toString());
        product2.setName(name);
        product2.setDescription("Descripcion2");
        List<Product> products = List.of(product1, product2);
        return products;
    }

    @Override
    public Optional<Product> updateProduct(String id, Product product) throws Exception {

        if (StringUtils.isEmpty(product.getId())) {
            throw new Exception("Id cannot be updated");
        }
        Optional<Product> found = findById(id);
        BeanUtils.copyProperties(product, found.get(), Utils.getNullPropertyNames(product));
        return found;
    }

    @Override
    public boolean deleteProduct(String id) {
        Optional<Product> found = findById(id);
        if (found.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<Product> findById(String id) {
        Product product = new Product();
        product.setId(id);
        product.setDescription("Ipad pro de 11 pulgadas");
        product.setName("Ipad pro");
        return Optional.of(product);
    }
}
