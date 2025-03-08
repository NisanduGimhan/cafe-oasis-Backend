package edu.icet.ecom.service;

import edu.icet.ecom.dto.Customer;

import java.util.List;

public interface CustomerService {
    void save(Customer customer);

    Customer findById(Long id);

    void deleteById(Long id);

    void update(Customer customer);

    List<Customer> findall();
}
