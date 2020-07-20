package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import co.edu.cedesistemas.commerce.service.interfaces.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private ProductRepository repository;

    public Product getById(final String id) {
        return repository.getById(id);
    }

    public List<Product> getProductsByName(String name){
        return repository.getAllByNameLike(name);
    }

    public Product updateProductById(String id){return repository.save(getById(id));}

    public Product deleteProductById(String id){
        try {
            repository.deleteById(id);
            return new Product();
        }catch (Exception e){
            log.debug(e.getMessage());
            return new Product();
        }
    }

    public Product createProduct(Product product){return repository.save(product);}
}
