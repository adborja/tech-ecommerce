package co.edu.cedesistemas.commerce.service;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@XSlf4j
@Service
//@AllArgsConstructor

public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(final Product product) {
        return repository.save(product);
    }

    public Product getById(final String id) {
        Optional<Product> storeId = repository.findById(id);
        if (storeId.isPresent()) {
            return storeId.get();
        } else {
            return null;
        }

    }

    public List<Product> getByName(final String name) {
        List<Product> productName = repository.findByNameLike(name);
        if(productName.isEmpty()) {return null;}
        else {      return productName;}
    }

    public Product updateProduct(String id, Product product) {
        Optional<Product> storeUpdate = repository.findById(id);
        if (storeUpdate.isPresent()) {
            return repository.save(product);
        } else {
            return null;
        }

    }

    public void deleteProduct(final String id)
    {
        repository.deleteById(id);
    }

}