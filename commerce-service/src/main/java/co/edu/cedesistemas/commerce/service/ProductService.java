package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository repository;

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Product> getByName(final String name) {
        return repository.findByNameLike(name);
    }

    public void  deleteProduct(String id){  repository.deleteById(id);}

    public Product updateProduct(String id, Product product) {

        Product productToSave = repository.findById(id).get();
        String[] ignoreA = Utils.getNullPropertyNames(product);
        BeanUtils.copyProperties(product,productToSave, Utils.getNullPropertyNames(product));
        return repository.save(productToSave);

    }
}
