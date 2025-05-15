package edu.icet.ecom.service.impl;

import edu.icet.ecom.dto.PaymentPayload;
import edu.icet.ecom.entity.CustomerEntity;
import edu.icet.ecom.entity.OrderItemEntity;
import edu.icet.ecom.entity.OrdersEntity;
import edu.icet.ecom.entity.PaymentEntity;
import edu.icet.ecom.repository.CustomerRepository;
import edu.icet.ecom.repository.OrderRepository;
import edu.icet.ecom.repository.PaymentRepository;
import edu.icet.ecom.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final CustomerRepository customerRepo;
    private final OrderRepository ordersRepo;
    private final PaymentRepository paymentRepo;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public void processSuccess(PaymentPayload payload) {

        String email = payload.getCustomer().getEmail();
        Optional<CustomerEntity> existingCustomerOpt = customerRepo.findByEmail(email);

        CustomerEntity customer;

        if (existingCustomerOpt.isPresent()) {

            customer = existingCustomerOpt.get();
            customer.setName(payload.getCustomer().getName());
            customer.setPhone(payload.getCustomer().getPhone());
            customer.setAddress(payload.getCustomer().getAddress());
        } else {

            customer = new CustomerEntity();
            customer.setName(payload.getCustomer().getName());
            customer.setEmail(email);
            customer.setPhone(payload.getCustomer().getPhone());
            customer.setAddress(payload.getCustomer().getAddress());
        }

        customerRepo.save(mapper.map(customer, CustomerEntity.class));

        OrdersEntity order = new OrdersEntity();
        order.setName(payload.getOrder().getName());
        order.setPhone(payload.getOrder().getPhone());
        order.setEmail(payload.getOrder().getEmail());
        order.setAddress(payload.getOrder().getAddress());
        order.setOrderNote(payload.getOrder().getOrderNote());
        order.setTax(payload.getOrder().getTax());
        order.setTotalPrice(payload.getOrder().getTotalPrice());
        order.setOrderNo(payload.getOrder().getOrderNo());

        order.setItems(
                payload.getOrder().getItems().stream().map(itemDto -> {
                    OrderItemEntity item = new OrderItemEntity();
                    item.setItemName(itemDto.getItemName());
                    item.setQuantity(itemDto.getQuantity());
                    item.setPrice(itemDto.getPrice());
                    item.setOrder(order);
                    return item;
                }).collect(Collectors.toList())
        );

        ordersRepo.save(mapper.map(order, OrdersEntity.class));

        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId(payload.getPayment().getOrderId());
        payment.setAmount(payload.getPayment().getAmount());
        payment.setCurrency(payload.getPayment().getCurrency());
        payment.setStatus(payload.getPayment().getStatus());
        payment.setPaymentDate(payload.getPayment().getPaymentDate());

        paymentRepo.save(mapper.map(payment, PaymentEntity.class));
    }
}
