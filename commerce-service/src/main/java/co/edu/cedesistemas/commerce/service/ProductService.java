package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Profile("!"+ SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements IProductService{
    private final ProductRepository repository;

    public Product createProduct(final Product product) {
        log.info("creating Product {}", product.getName());
        product.setId(UUID.randomUUID().toString());
        return repository.save(product);
    }

    public Product getById(final String id) {
        log.info("retrieving Product by Id {}", id);
        return repository.findById(id).get();
    }

    public List<Product> getByName(final String name) {
        log.info("retrieving Product by Name {}", name);
        return repository.findByNameLike(name);
    }

    public Product updateProduct(String id, Product product) {
        Product productFind = repository.findById(id).get();
        if(productFind==null){
            log.warn("Product not found: {}", id);
            return null;
        }
        BeanUtils.copyProperties(product,productFind, Utils.getNullPropertyNames(product));
        return repository.save(productFind);
    }

    public void deleteProduct(String id) {
        log.info("deleting Product by Id {}", id);
        repository.deleteById(id);
    }
}
