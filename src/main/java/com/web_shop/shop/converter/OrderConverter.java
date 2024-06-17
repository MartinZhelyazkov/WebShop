package com.web_shop.shop.converter;
import com.web_shop.shop.dto.OrderRequest;
import com.web_shop.shop.model.Order;
import org.springframework.stereotype.Component;
import java.time.Instant;
@Component
public class OrderConverter {
    public Order toOrder(OrderRequest orderRequest){
        Order order = new Order();
        Instant orderDate = Instant.now();
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setOrderDate(orderDate);
        return order;
    }

}
