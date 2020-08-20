package co.edu.cedesistemas.commerce.shipping.repository;


import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {

    Optional<Shipment> findByTrackNumber (String trackNumber);

}
