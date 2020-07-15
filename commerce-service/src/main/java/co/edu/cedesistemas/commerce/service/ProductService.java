package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository repository;

    public Product createProduct(final Product product){
        product.setId(UUID.randomUUID().toString());
        return repository.save(product);
    }

    public Product getById(final String id){
        return repository.findById(id).orElse(null);
    }

    public List<Product> getByName(final String name){
        return repository.findByNameLike(name);
    }

    public Product updateProduct(final String id, final Product product){
        Product actualProduct = getById(id);
        if(actualProduct == null){
            return null;
        }
        BeanUtils.copyProperties(product, actualProduct, Utils.getNullPropertyNames(product));
        return repository.save(actualProduct);
    }

    public void deleteProduct(final String id){
        repository.deleteById(id);
    }
}
