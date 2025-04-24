package com.Task_Forge.Microservice.MailService;

import com.Task_Forge.Microservice.MailService.RegisterMailService;
import com.Task_Forge.Microservice.MailService.AuthenticateMailService;
import com.Task_Forge.Microservice.MailService.CreateCompanyMailService;
import com.Task_Forge.Microservice.MailService.CreateProjectMailService;


public class MailServiceTest {
    public static void main(String[] args) {
        String recipient = "test@gmail.com"; // Change this to a real email
        String otp = RegisterMailService.generateOTP();

        RegisterMailService.sendOTP(recipient, otp);
        AuthenticateMailService.sendOTP(recipient, otp);
        CreateCompanyMailService.sendOTP(recipient, otp);
        CreateProjectMailService.sendOTP(recipient, otp);

        System.out.println("Emails Sent Successfully!");
    }
}
