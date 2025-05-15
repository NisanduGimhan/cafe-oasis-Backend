package edu.icet.ecom.service;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.dto.Orders;

public interface EmailService {
    public void sendOrderConfirmationEmail(Customer customer, Orders order);

}
