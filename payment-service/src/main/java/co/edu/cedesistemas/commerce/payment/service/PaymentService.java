package co.edu.cedesistemas.commerce.payment.service;

import co.edu.cedesistemas.commerce.payment.model.Payment;
import co.edu.cedesistemas.commerce.payment.model.PaymentStatus;
import co.edu.cedesistemas.commerce.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository repository;
    private final EventPublisherService publisherService;

    public Payment createPayment(final Payment payment) {
        payment.setId(UUID.randomUUID().toString());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING);

        if (payment.getValue() <= 100000) {
            payment.setStatus(PaymentStatus.APPROVED);
        }

        Payment result = repository.save(payment);

        publisherService.publishPaymentEvent(result);

        return result;
    }

    public Payment approve(final String id) {
        Payment found = getById(id);
        if (found == null) {
            return null;
        }
        found.setStatus(PaymentStatus.APPROVED);

        Payment result = repository.save(found);

        publisherService.publishPaymentEvent(found);

        return result;
    }

    public Payment reject(final String id) {
        Payment found = getById(id);
        if (found == null) {
            return null;
        }
        found.setStatus(PaymentStatus.REJECTED);

        Payment result = repository.save(found);

        publisherService.publishPaymentEvent(found);

        return result;
    }

    public Payment getById(final String id) {
        return repository.findById(id).orElse(null);
    }
}
