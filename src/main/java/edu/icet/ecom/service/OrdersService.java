package edu.icet.ecom.service;

import edu.icet.ecom.dto.Orders;

import java.util.List;
import java.util.Optional;

public interface OrdersService {
    void save(Orders orders);

    void deleteById(Long id);

    List<Orders> findall();

}
