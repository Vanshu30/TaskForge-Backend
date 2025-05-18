package com.Task_Forge.Microservice.Service;

import com.Task_Forge.Microservice.Entity.Company;
import com.Task_Forge.Microservice.Exception.ResourceNotFoundException;
import com.Task_Forge.Microservice.Repository.CompanyRepository;
import com.Task_Forge.Microservice.Repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public String createOrder(UUID companyId, double amount) throws RazorpayException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        RazorpayClient razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(amount * 100)); // Razorpay requires amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + companyId);
        orderRequest.put("payment_capture", 1); // Auto-capture

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }
}
