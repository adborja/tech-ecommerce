package co.edu.cedesistemas.commerce.payment.repository;

import co.edu.cedesistemas.commerce.payment.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
}
