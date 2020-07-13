package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository repository;

    public Product getById(final String id) {
        return null;
    }

    public List<Product> getProductsByName(String name){
        return null;
    }

    public Product updateProductById(String id){return null;}

    public Product deleteProductById(String id){return null;}

    public Product createProduct(Product product){return repository.save(product);}
}
