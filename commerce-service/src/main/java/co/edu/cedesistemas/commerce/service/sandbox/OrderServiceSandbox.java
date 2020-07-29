package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.*;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.OrderStatus;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@AllArgsConstructor
@Service
public class OrderServiceSandbox implements IOrderService {

    @Override
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        return  order;
    }

    @Override
    public Order updateOrder(String id, Order order) throws Exception {
        if (order.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        Order found = getById(id);
        BeanUtils.copyProperties(order, found, Utils.getNullPropertyNames(order));
        return found;
    }

    @Override
    public Order getById(String id)  throws Exception{


        // OrderItems
        final OrderItem orderItem = new OrderItem();
        orderItem.setProductId(UUID.randomUUID().toString());
        orderItem.setFinalPrice(123.2F);
        orderItem.setQuantity(12);


        return Order.builder()
                .id(id)
                .userId(UUID.randomUUID().toString())
                .storeId(UUID.randomUUID().toString())
                .shippingAddressId(UUID.randomUUID().toString())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .items(List.of(orderItem))
                .build();

    }

    @Override
    public List<OrderItem> getOrderItems(String id) {
        return null;
    }
}
