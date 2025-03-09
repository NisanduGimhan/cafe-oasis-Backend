package edu.icet.ecom.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "OrderItem")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Double price;
    private Integer quantity;

    public void setOrder(OrdersEntity save) {
    }
}
