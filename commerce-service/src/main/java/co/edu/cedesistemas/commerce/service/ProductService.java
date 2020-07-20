package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository repository;

    public Product createProduct(final Product product) {
         return repository.save(product);
    }

    public Product updateProduct(final Product product) {
       Product updated = getProduct(product.getId()).get();
       BeanUtils.copyProperties(product,updated, Utils.getNullPropertyNames(product));
       return repository.save(updated);
    }


    public void deleteProduct(final Product product) {
        repository.delete(product);
    }

    public Optional<Product> getProduct(final String id) {
           return repository.findById(id);
    }

    public List<Product> getProductbyName(final String name) {
        return repository.findByNameLike(name);
    }
}
