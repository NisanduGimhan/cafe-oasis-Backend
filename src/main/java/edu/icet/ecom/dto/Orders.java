package edu.icet.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {
    private Long id;
    private String orderNo;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String orderNote;
    private Double tax;
    private Double totalPrice;
    private LocalDateTime date;
    private List<OrderItem> items = new ArrayList<>();
}
