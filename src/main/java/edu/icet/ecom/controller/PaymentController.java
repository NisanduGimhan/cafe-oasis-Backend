package edu.icet.ecom.controller;

import edu.icet.ecom.dto.Customer;
import edu.icet.ecom.dto.Orders;
import edu.icet.ecom.dto.Payment;
import edu.icet.ecom.dto.PaymentPayload;
import edu.icet.ecom.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.DecimalFormat;
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

            // Generate the MD5 signature to verify notification
            String localMd5sig = getMd5(merchantId + orderId + payhereAmount + payhereCurrency + statusCode + getMd5(merchantSecret));

            // Check if the MD5 signature matches and if the status code is "2" (payment successful)
            if (localMd5sig.equalsIgnoreCase(md5sig) && "2".equals(statusCode)) {
                // Create Customer DTO from request parameters
                Customer customer = new Customer();
                customer.setName(request.getParameter("first_name"));
                customer.setEmail(request.getParameter("email"));
                customer.setPhone(request.getParameter("phone"));
                customer.setAddress(request.getParameter("address"));

                // Create Order DTO from request parameters
                Orders order = new Orders();
                order.setOrderNo(orderId);  // Match PayHere order number
                order.setName(request.getParameter("first_name"));
                order.setEmail(request.getParameter("email"));
                order.setPhone(request.getParameter("phone"));
                order.setAddress(request.getParameter("address"));
                order.setOrderNote(request.getParameter("order_note") != null ? request.getParameter("order_note") : "No notes");
                order.setTotalPrice(Double.parseDouble(payhereAmount));
                order.setTax(0.0); // Set tax to 0, you can adjust if needed

                // Create Payment DTO from request parameters
                Payment payment = new Payment();
                payment.setOrderId(orderId);
                payment.setAmount(Double.parseDouble(payhereAmount));
                payment.setCurrency(payhereCurrency);
                payment.setStatus("SUCCESS");
                payment.setPaymentDate(java.time.LocalDateTime.now());

                // Create the PaymentPayload and process it
                PaymentPayload payload = new PaymentPayload();
                payload.setCustomer(customer);
                payload.setOrder(order);
                payload.setPayment(payment);

                processSuccess(payload);

                return ResponseEntity.ok("Payment successful");
            } else {
                // If the MD5 signature is invalid or payment failed
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
            // You can log the success here if necessary
            System.out.println("Payment success recorded and processed.");
        } catch (Exception e) {
            e.printStackTrace();
            // You can add error logging here if necessary
        }
    }
}