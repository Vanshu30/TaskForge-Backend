package com.Task_Forge.Microservice.Controller;

import com.Task_Forge.Microservice.DTO.PaymentDTO;
import com.Task_Forge.Microservice.Service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/checkout")
    public ResponseEntity<String> createPaymentOrder(@RequestBody PaymentDTO paymentDTO) {
        try {
            String orderResponse = paymentService.createOrder(paymentDTO.getCompanyId(), paymentDTO.getAmount());
            return ResponseEntity.ok(orderResponse);
        } catch (RazorpayException e) {
            return ResponseEntity.status(500).body("Error creating payment order: " + e.getMessage());
        }
    }
}
