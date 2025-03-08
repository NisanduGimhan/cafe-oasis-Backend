package edu.icet.ecom.repository;

import edu.icet.ecom.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
