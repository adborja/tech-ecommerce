package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private ProductRepository repository;

    public Product createProduct(final String id) {
        log.debug("Creando producto: " + id);
        Product product = new Product();
        product.setId(id);
        return repository.save(product);
    }

    public Product update(final Product product) {
        return repository.save(product);
    }

    public Product getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public Set<Product> getByUserLiked(final String userId) {
        return repository.findByUserLiked(userId);
    }

    public Product getProduct(String id) {
        log.debug("obteniendo producto: " + id);
        Product product = getById(id);
        if (product == null) {
            product = new Product();
            product.setId(id);
            product = update(product);
        }
        return product;
    }
}
