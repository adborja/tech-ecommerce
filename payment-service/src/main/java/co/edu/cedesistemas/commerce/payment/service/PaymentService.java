package co.edu.cedesistemas.commerce.payment.service;

import co.edu.cedesistemas.commerce.payment.client.CreditCard;
import co.edu.cedesistemas.commerce.payment.client.Order;
import co.edu.cedesistemas.commerce.payment.client.PaymentGatewayClientWrapper;
import co.edu.cedesistemas.commerce.payment.client.Person;
import co.edu.cedesistemas.commerce.payment.client.TransactionReq;
import co.edu.cedesistemas.commerce.payment.client.TransactionRes;
import co.edu.cedesistemas.commerce.payment.model.Payment;
import co.edu.cedesistemas.commerce.payment.model.PaymentStatus;
import co.edu.cedesistemas.commerce.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentGatewayClientWrapper paymentGateway;
    private final EventPublisherService publisherService;

    public Payment createPayment(final Payment payment) {
        payment.setId(UUID.randomUUID().toString());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING);

        log.info("calling external payment gateway");
        TransactionReq txReq = toTransaction(payment);
        TransactionRes res = paymentGateway.pay(txReq);

        return repository.save(payment);
    }

    public Payment getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public Payment confirm(final String id, final TransactionRes res) {
        log.info("confirming transaction: {}. Status: {}, Authorization code: {}", res.getId(),
                res.getState(), res.getAuthorizationCode());
        Payment found = getById(id);
        if (found == null) {
            return null;
        }

        found.setStatus(PaymentStatus.valueOf(res.getState()));
        found.setAuthorizationCode(res.getAuthorizationCode());
        found.setResponseMessage(res.getResponseMessage());
        Payment result = repository.save(found);

        publisherService.publishPaymentEvent(found);

        return result;
    }

    private static TransactionReq toTransaction(final Payment payment) {
        String orderId = payment.getOrderId();
        String notifyUrl =
                "http://gateway.cedesistemas.local:8080/cedesistemas/payment/api/v1/payments/"
                        + payment.getId() + "/confirmation";
        TransactionReq paymentReq = new TransactionReq();
        paymentReq.setOrder(Order.builder()
                .id(payment.getId())
                .accountId(payment.getUserId())
                .referenceCode("payment_" + orderId)
                .description("payment from tech-ecommerce")
                .signature(RandomStringUtils.randomAlphanumeric(10))
                .notifyUrl(notifyUrl)
                .value(payment.getValue())
                .buyer(Person.builder()
                        .merchantId(payment.getStoreId())
                        .email(payment.getUserEmail())
                        .dni(payment.getUserDni())
                        .fullName(payment.getUserFullName())
                        .phone(payment.getUserPhone())
                        .build())
                .build());
        paymentReq.setCreditCard(CreditCard.builder()
                .name(payment.getCreditCard().getName())
                .expirationMonth(payment.getCreditCard().getExpirationMonth())
                .expirationYear(payment.getCreditCard().getExpirationYear())
                .number(payment.getCreditCard().getNumber())
                .securityCode(payment.getCreditCard().getSecurityCode())
                .build());
        paymentReq.setType("AUTHORIZATION_AND_CAPTURE");
        paymentReq.setDeviceSessionId(UUID.randomUUID().toString());
        paymentReq.setPaymentMethod(payment.getCreditCard().getBrand());
        paymentReq.setPaymentCountry("CO");
        paymentReq.setIpAddress("127.0.0.1");
        paymentReq.setCookie(RandomStringUtils.randomAlphanumeric(15));
        paymentReq.setUserAgent("Mozilla");

        return paymentReq;
    }
}
