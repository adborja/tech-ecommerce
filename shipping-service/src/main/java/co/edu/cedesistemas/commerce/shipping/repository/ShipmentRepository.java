package co.edu.cedesistemas.commerce.shipping.repository;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ShipmentRepository extends MongoRepository<Shipment, String> {
    Optional<Shipment> getById(String id);
    Optional<Shipment> getByTrackNumber(String trackNumber);

}
