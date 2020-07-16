package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
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
        return product;
    }

    @Override
    public Product getProductById(String id) {
        Product product =  new Product();
        product.setId(id);
        product.setName("Producto de prueba");
        product.setDescription("esto es la descripcion");
        return product;
    }

    @Override
    public void deleteProduct(String id) {
        Product product =  new Product();
        product.setId(id);
        product.setName("Producto de prueba");
        product.setDescription("esto es la descripcion");

    }

    @Override
    public List<Product> getProductByName(String name) {
        Product product1 = new Product();
        product1.setId(UUID.randomUUID().toString());
        product1.setName(name);
        product1.setDescription("Pestañina negra");

        Product product2 = new Product();
        product2.setId(UUID.randomUUID().toString());
        product2.setName(name);
        product2.setDescription("Rubor color dorado");

        return Arrays.asList(product1,product2);
    }

    @Override
    public Product updateProduct(String id, Product product) throws Exception {
        if(product.getId()!=null) {
            throw new Exception("Id no pudo ser actualizado");
        }
        Product found =getProductById(id);
        BeanUtils.copyProperties(product,found, Utils.getNullPropertyNames(product));
        return found;
    }
}
