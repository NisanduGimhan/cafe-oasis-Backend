package edu.icet.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {
    private Long id;
    private String customerName;
    private String contactNo;
    private Double discount;
    private Double totalPrice;
    private List<OrderItem> items;
}
