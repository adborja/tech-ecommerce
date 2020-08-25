package co.edu.cedesistemas.commerce.shipping.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;

@Repository
public interface ShipmentRepository extends MongoRepository<Shipment, String>{

	public Optional<Shipment> findByTrackNumber(String trackNumber);
}
