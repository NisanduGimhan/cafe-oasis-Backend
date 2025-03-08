package edu.icet.ecom.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Item")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemNo;
    private String itemType;
    private String name;
    private Double price;
    private String imageUrl;
}
