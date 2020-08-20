package co.edu.cedesistemas.commerce.shipping.repository;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShippingRepository extends MongoRepository<Shipment,String> {
}
