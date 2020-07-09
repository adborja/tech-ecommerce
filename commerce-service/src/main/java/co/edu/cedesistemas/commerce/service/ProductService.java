package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product getProductById(String id){
        return productRepository.findById(id)
                .orElse(null);
    }

    public List<Product> getProductByName(String name){
        return productRepository.findByNameLike(name);
    }

    public Product updateProduct(Product productToUpdate, String productId){
        return productRepository.findById(productId)
                .map(actualProduct -> updateProduct(actualProduct, productToUpdate))
                .map(productRepository::save)
                .orElse(null);
    }

    private Product updateProduct(Product actualProduct, Product productToUpdate){
        actualProduct.setDescription(productToUpdate.getDescription());

        return actualProduct;
    }

    public void deleteProduct(String productId){
        productRepository.findById(productId)
                .ifPresent(productRepository::delete);
    }
}
