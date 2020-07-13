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
public class ProductServiceSandbox implements IProductService{

    @Override
    public Product createProduct(final Product product) {
        product.setName("Pan");
        product.setDescription("Pan Bimbo");
        product.setId(UUID.randomUUID().toString());
        return product;
    }

    @Override
    public Product getById(final String id)
    {
        Product product = new Product();
        product.setId(id);
        product.setDescription("Producto nuevo" + id);
        product.setName("Nombre " + id);
        return product;
    }

    @Override
    public List<Product> getByName(final String name)
    {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setDescription("producto 1");
        product.setName("producto-" +name);

        Product product2 = new Product();
        product2.setId(UUID.randomUUID().toString());
        product2.setDescription("producto 2");
        product2.setName("prod2-"+name);

        return Arrays.asList(product,product2);
    }

    @Override
    public Product updateProduct(String id, Product product)  throws Exception  {
        if (product.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        Product found = getById(id);
        BeanUtils.copyProperties(product, found, Utils.getNullPropertyNames(product));
        return found;
    }

    @Override
    public void deleteProduct(final String id)
    {


    }

}
