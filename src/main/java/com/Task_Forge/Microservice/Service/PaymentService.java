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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {

    private static final String RAZORPAY_KEY = "api_key";
    private static final String RAZORPAY_SECRET = "api_secret";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public String createOrder(UUID companyId, double amount) throws RazorpayException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()-> new ResourceNotFoundException("Company not found"));

        RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }
}
