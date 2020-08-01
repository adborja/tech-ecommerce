package co.edu.cedesistemas.commerce.inventory.service;

import co.edu.cedesistemas.commerce.inventory.model.Product;
import co.edu.cedesistemas.commerce.inventory.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;

    public Product createProduct(final Product product) {
        product.setId(UUID.randomUUID().toString());
        product.setCreatedAt(LocalDateTime.now());
        product.setActive(true);
        return repository.save(product);
    }

    public Product getById(final String id) {
        return repository.findById(id).orElse(null);
    }
}
