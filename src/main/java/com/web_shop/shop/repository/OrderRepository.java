package com.web_shop.shop.repository;
import com.web_shop.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order,Long> {
}
