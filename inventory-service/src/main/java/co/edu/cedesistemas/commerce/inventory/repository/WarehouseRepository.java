package co.edu.cedesistemas.commerce.inventory.repository;

import co.edu.cedesistemas.commerce.inventory.model.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
}
