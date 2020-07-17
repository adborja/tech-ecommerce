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

@Profile("!"+ SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository repository;

    public Product createProduct(final Product product) {
        product.setId(UUID.randomUUID().toString());
        return repository.save(product);
    }

    public Product getById(final String id) {
        return repository.findById(id).get();
    }

    public List<Product> getByName(final String name) {
        return repository.findByNameLike(name);
    }

    public Product updateProduct(String id, Product product) {
        Product productFind = repository.findById(id).get();
        BeanUtils.copyProperties(product,productFind, Utils.getNullPropertyNames(product));
        return repository.save(productFind);
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }
}
