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
import java.util.Optional;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ProductService implements IProductService{

    ProductRepository repository;

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        return repository.save(product);
    }

    public Product getProductById(String id) {
        Optional<Product> optional = repository.findById(id);
        if(optional.isEmpty()) return null;
        else {
            Product product = optional.get();
            return product;
        }
    }

    public Product deleteProductById(String id) {
        Optional<Product> optional = repository.findById(id);
        if(optional.isEmpty()) return null;
        else {
          Product product = optional.get();
          repository.deleteById(id);
          return product;
        }
    }

    public List<Product> getProductByName(String name) {
        return repository.findByNameLike(name);
    }

    public Product updateProductById(String id, Product product) {
        Optional<Product> optional = repository.findById(id);
        if(optional.isEmpty()) return null;
        else {
            Product productUpdated = optional.get();
            BeanUtils.copyProperties(product,productUpdated,Utils.getNullPropertyNames(product));
            repository.save(productUpdated);
            return productUpdated;
        }
    }
}
