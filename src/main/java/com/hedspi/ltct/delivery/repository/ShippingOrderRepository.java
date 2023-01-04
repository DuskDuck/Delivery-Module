package com.hedspi.ltct.delivery.repository;

import com.hedspi.ltct.delivery.model.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {
}