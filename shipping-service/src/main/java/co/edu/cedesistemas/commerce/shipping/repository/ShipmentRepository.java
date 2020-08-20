package co.edu.cedesistemas.commerce.shipping.repository;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShipmentRepository extends MongoRepository<Shipment,String> {
    Shipment findByTrackNumber(String trackName);
}
