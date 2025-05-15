package edu.icet.ecom.service.impl;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.dto.OrderItem;
import edu.icet.ecom.dto.Orders;
import edu.icet.ecom.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Override
    public void sendOrderConfirmationEmail(Customer customer, Orders order) {
        String subject = "Order Confirmation - Order #" + order.getOrderNo();
        StringBuilder content = new StringBuilder();
        content.append("Hello ").append(customer.getName()).append(",\n\n");
        content.append("Thank you for your order!\n");
        content.append("Here are your order details:\n\n");
        content.append("Order ID: ").append(order.getOrderNo()).append("\n");
        content.append("Name: ").append(order.getName()).append("\n");
        content.append("Phone: ").append(order.getPhone()).append("\n");
        content.append("Address: ").append(order.getAddress()).append("\n");
        content.append("Total Price: LKR ").append(order.getTotalPrice()).append("\n");
        content.append("Order Note: ").append(order.getOrderNote()).append("\n\n");
        content.append("Items:\n");

        for (OrderItem item : order.getItems()) {
            content.append("- ")
                    .append(item.getItemName())
                    .append(" x ")
                    .append(item.getQuantity())
                    .append(" @ LKR ")
                    .append(item.getPrice())
                    .append("\n");
        }

        content.append("\nWe will contact you soon regarding the delivery.\n");
        content.append("Cheers,\nOasis_Cafe Team");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject(subject);
        message.setText(content.toString());
        mailSender.send(message);
    }

}
