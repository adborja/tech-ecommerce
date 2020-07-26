package co.edu.cedesistemas.commerce.sandbox;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;

@Profile(SpringProfile.SANDBOX)
public class ProductServiceSandbox implements IProductService{

	@Override
	public Product createProduct(Product product) {
		product.setId(UUID.randomUUID().toString());
		return product;
	}

	@Override
	public void deleteProduct(String id) {
		
	}

	@Override
	public Product getById(String id) {
		Product p1 = new Product();
		p1.setName("producto1");
		p1.setId(id);
		p1.setDescription("descripcion1");
		
		return p1;
	}

	@Override
	public List<Product> getByName(String name) throws Exception {
		Product p1 = new Product();
		p1.setName(name + " producto1");
		p1.setId(UUID.randomUUID().toString());
		p1.setDescription("descripcion1");
		
		Product p2 = new Product();
		p2.setName(name + " producto2");
		p2.setId(UUID.randomUUID().toString());
		p2.setDescription("descripcion2");
		
		Product p3 = new Product();
		p3.setName(name + "producto3");
		p3.setId(UUID.randomUUID().toString());
		p3.setDescription("descripcion3");
		
		return java.util.Arrays.asList(p1,p2,p3);
	}

	@Override
	public Product updateProduct(String id, Product product) throws Exception{
		if (product.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        Product found = getById(id);
        BeanUtils.copyProperties(product, found, Utils.getNullPropertyNames(product));
        return found;
	}

}
