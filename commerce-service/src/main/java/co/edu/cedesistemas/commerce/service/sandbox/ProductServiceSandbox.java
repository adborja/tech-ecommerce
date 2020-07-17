package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
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
        product.setName("product example");
        return product;
    }

    @Override
    public Product getById(String id) {
        Product product = new Product();
        product.setDescription("desc 1");
        product.setId(id);
        product.setName("prod 1");
        return product;
    }

    @Override
    public List<Product> getByName(String name) {
        //create products
        Product product = new Product();
        product.setDescription("desc 1");
        product.setId(UUID.randomUUID().toString());
        product.setName("prod 1");

        Product product2 = new Product();
        product.setDescription("desc 2");
        product.setId(UUID.randomUUID().toString());
        product.setName("prod 2");

        return Arrays.asList(product,product2);
    }

    @Override
    public Product updateProduct(String id, Product product) throws Exception{
        if (product.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        Product found = getById(id);
        BeanUtils.copyProperties(product, found, Utils.getNullPropertyNames(product));
        return found;
    }

    @Override
    public void deleteProduct(String id) { }
}
