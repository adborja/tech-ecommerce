package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(String id){
        return productRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Product> getProductByName(String name){
        return productRepository.findByNameLike(name);
    }

    @Override
    public Product updateProduct(Product productToUpdate, String productId){
        return productRepository.findById(productId)
                .map(actualProduct -> updateProductObject(actualProduct, productToUpdate))
                .map(productRepository::save)
                .orElse(null);
    }

    private Product updateProductObject(Product actualProduct, Product productToUpdate){
        actualProduct.setDescription(productToUpdate.getDescription());

        return actualProduct;
    }

    @Override
    public void deleteProduct(String productId){
        productRepository.findById(productId)
                .ifPresent(productRepository::delete);
    }
}
