package edu.icet.ecom.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "order") // Prevents infinite recursion
@Entity
@Table(name = "order_items") // Standardized table name
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // Ensuring correct FK name
    private OrdersEntity order;
}
