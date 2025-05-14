package edu.icet.ecom.service;

import edu.icet.ecom.dto.Payment;
import edu.icet.ecom.dto.PaymentPayload;

import java.util.List;

public interface PaymentService {
    void processSuccess(PaymentPayload payload);
}
