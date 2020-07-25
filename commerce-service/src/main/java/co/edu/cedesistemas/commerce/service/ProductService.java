package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
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
@Profile("!"+ SpringProfile.SANDBOX)

public class ProductService implements IProductService {

    ProductRepository repository;

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        return repository.save(product);
    }

    public Product getProductById(String id) {
        Product product =   repository.findById(id).orElse(null);
        if (product == null) {
            return null;
        } else{
            return product;
        }

    }

    public Product deleteProductById(String id) {
        Product product =   repository.findById(id).orElse(null);

        if (product == null) {
            return null;
        } else{
            repository.deleteById(id);
        }
       return product;


    }

    public List<Product> getProductByName(String name) {
        return repository.findByNameLike(name);
    }

    public Product updateProductById(String id, Product product) {
        Product productUpdate =   repository.findById(id).orElse(null);

        if (productUpdate == null){
            return null;
        } else {
            BeanUtils.copyProperties(product, productUpdate, Utils.getNullPropertyNames(product));
            repository.save(productUpdate);
            return productUpdate;
        }
    }
}
