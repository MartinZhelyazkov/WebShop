package com.web_shop.shop.repository;
import com.web_shop.shop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
