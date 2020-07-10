package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> getProductByNamePrefix(String name) {
        return this.productRepository.findByNameLike(name);
    }

    public Optional<Product> updateProduct(String id, Product product) {
        Product foundProduct = this.productRepository.findById(id).orElse(null);

        if (foundProduct != null) {
            BeanUtils.copyProperties(product, foundProduct, Utils.getNullPropertyNames(product));
            return Optional.of(this.productRepository.save(foundProduct));
        }
        return Optional.empty();
    }

    public boolean deleteProduct(String id) {

        Optional<Product> foundProduct = this.productRepository.findById(id);

        if (foundProduct.isPresent()) {
            this.productRepository.delete(foundProduct.get());
            return true;
        }
        return false;
    }

    public Optional<Product> findById(String id) {
        return this.productRepository.findById(id);
    }
}
