package edu.icet.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.ecom.dto.*;
import edu.icet.ecom.service.EmailService;
import edu.icet.ecom.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static edu.icet.ecom.util.PayHereHashUtil.getMd5;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    private final String merchantId = "1230343"; // replace with sandbox ID
    private final String merchantSecret = "Mjg1NzgyMjk0MjIyNTg0NjM5ODgxNzQxNTI2MjQzODA4Mjc3MDg3";
    private final EmailService emailService;
    // Endpoint to generate hash
    @PostMapping("/hash")
    public ResponseEntity<Map<String, String>> generateHash(@RequestBody Map<String, String> payload) {
        String orderId = payload.get("order_id");
        String amountStr = payload.get("amount");
        String currency = payload.get("currency");

        // Parse the amount and format it to 2 decimal places
        double amount = Double.parseDouble(amountStr);
        DecimalFormat df = new DecimalFormat("0.00");
        String amountFormatted = df.format(amount);

        // Generate the MD5 hash based on provided parameters
        String hash = getMd5(merchantId + orderId + amountFormatted + currency + getMd5(merchantSecret));
        System.out.println(hash);

        // Return the generated hash and merchant ID
        return ResponseEntity.ok(Map.of(
                "hash", hash,
                "merchant_id", merchantId
        ));
    }

    // Endpoint to handle PayHere payment notification
    @PostMapping("/notify")
    public ResponseEntity<String> handleNotify(HttpServletRequest request) {
        try {
            // Extract parameters from PayHere notification
            String merchantId = request.getParameter("merchant_id");
            String orderId = request.getParameter("order_id");
            String payhereAmount = request.getParameter("payhere_amount");
            String payhereCurrency = request.getParameter("payhere_currency");
            String statusCode = request.getParameter("status_code");
            String md5sig = request.getParameter("md5sig");
            String customDataJson = request.getParameter("custom_1"); // Retrieve custom_1 data

            // Generate the MD5 signature to verify notification
            String localMd5sig = getMd5(merchantId + orderId + payhereAmount + payhereCurrency + statusCode + getMd5(merchantSecret));

            if (localMd5sig.equalsIgnoreCase(md5sig) && "2".equals(statusCode)) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> customData = objectMapper.readValue(customDataJson, Map.class);

                // Extract customer details from customData
                Map<String, Object> customerMap = (Map<String, Object>) customData.get("customer");
                Customer customer = new Customer();
                customer.setName((String) customerMap.get("name"));
                customer.setEmail((String) customerMap.get("email"));
                customer.setPhone((String) customerMap.get("phone"));
                customer.setAddress((String) customerMap.get("address"));

                // Extract order details from customData
                Map<String, Object> orderMap = (Map<String, Object>) customData.get("order");
                Orders order = new Orders();
                order.setOrderNo(orderId);
                order.setName(customer.getName());
                order.setEmail(customer.getEmail());
                order.setPhone(customer.getPhone());
                order.setAddress(customer.getAddress());
                order.setOrderNote(orderMap.get("notes") != null ? (String) orderMap.get("notes") : "No notes");
                order.setTotalPrice(Double.parseDouble(payhereAmount));
                order.setTax(0.0);

                // Handle order items if necessary
                List<Map<String, Object>> items = (List<Map<String, Object>>) orderMap.get("items");
                List<OrderItem> orderItems = new ArrayList<>();
                for (Map<String, Object> item : items) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setItemName((String) item.get("itemName"));
                    orderItem.setPrice(((Number) item.get("price")).doubleValue());

                    orderItem.setQuantity((Integer) item.get("quantity"));
                    orderItems.add(orderItem);
                }
                order.setItems(orderItems);

                // Create Payment DTO
                Payment payment = new Payment();
                payment.setOrderId(orderId);
                payment.setAmount(Double.parseDouble(payhereAmount));
                payment.setCurrency(payhereCurrency);
                payment.setStatus("SUCCESS");
                payment.setPaymentDate(LocalDateTime.now());

                PaymentPayload payload = new PaymentPayload();
                payload.setCustomer(customer);
                payload.setOrder(order);
                payload.setPayment(payment);

                processSuccess(payload);

                return ResponseEntity.ok("Payment successful");
            } else {
                return ResponseEntity.status(400).body("Invalid notification");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error");
        }
    }


    // Handle payment return success URL (Redirects to success page)
    @GetMapping("/return")
    public RedirectView handleReturn() {
        return new RedirectView("http://localhost:4200/payment-success");
    }

    // Handle payment cancellation URL (Redirects to cancel page)
    @GetMapping("/cancel")
    public RedirectView handleCancel() {
        return new RedirectView("http://localhost:4200/payment-cancel");
    }

    // Method to process successful payment
    public void processSuccess(PaymentPayload payload) {
        try {
            paymentService.processSuccess(payload);
            System.out.println(payload);
            // You can log the success here if necessary
            System.out.println("Payment success recorded and processed.");
            emailService.sendOrderConfirmationEmail(payload.getCustomer(), payload.getOrder());
        } catch (Exception e) {
            e.printStackTrace();
            // You can add error logging here if necessary
        }
    }
}