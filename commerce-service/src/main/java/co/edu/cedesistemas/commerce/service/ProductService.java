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

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository repository;

    public Product createProduct(final Product product) {
        return repository.save(product);
    }

    @Override
    public Product getProductById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Product getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteProduct(final String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return null;
    }

    public List<Product> getByName(final String name) {
        return repository.findByNameLike(name);
    }

    public Product updateProduct(String id, Product product) {
        Product productToUpdate = repository.findById(id).get();
        BeanUtils.copyProperties(product, productToUpdate, Utils.getNullPropertyNames(product));
        return repository.save(productToUpdate);
    }
}
