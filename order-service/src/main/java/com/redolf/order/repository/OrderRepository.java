package com.redolf.order.repository;

import com.redolf.order.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderRepository extends JpaRepository<PurchaseOrder,Integer> {
}
