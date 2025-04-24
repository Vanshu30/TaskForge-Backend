package com.Task_Forge.Microservice.MailService;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final Map<String, OTPDetails> otpStorage = new HashMap<>();
    private final Map<String, InvitationDetails> invitationStorage = new HashMap<>();

    @Value("${spring.mail.username}")
    private String senderEmail;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Send OTP for Verification (expires in 5 minutes)
    public void sendOTP(String recipientEmail) {
        String otp = generateOTP();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        otpStorage.put(recipientEmail, new OTPDetails(otp, expiryTime));

        String subject = "Your OTP Code";
        String body = "Your OTP for verification is: <b>" + otp + "</b>. It expires in 5 minutes.";

        sendEmail(recipientEmail, subject, body);
    }

    public void sendEmployeeInvite(String recipientEmail, String organization) {
        String subject = "Invitation to Join " + organization;
        String body = "<p>Dear " + recipientEmail + ",</p>"
                + "<p>You are invited to join <b>" + organization + "</b>. Please accept the invitation.</p>"
                + "<p>Best regards,<br>Team</p>";

        sendEmail(recipientEmail, subject, body);
    }


    // Verify OTP
    public boolean verifyOTP(String email, String enteredOTP) {
        OTPDetails details = otpStorage.get(email);
        if (details == null || LocalDateTime.now().isAfter(details.getExpiryTime())) {
            return false; // OTP expired or not found
        }
        return details.getOtp().equals(enteredOTP);
    }

    // Send Employee Invitation (Valid for 7 Days)
    public void sendOfficialInvitation(String recipientEmail, String organizationName,
                                       String eventName, String eventDate,
                                       String eventTime, String location) {
        String inviteToken = generateOTP();  // Unique token
        String rsvpLink = "https://yourapp.com/rsvp?token=" + inviteToken;
        LocalDateTime expiryTime = LocalDateTime.now().plusDays(7);

        invitationStorage.put(inviteToken, new InvitationDetails(recipientEmail, organizationName, eventName,
                eventDate, eventTime, location, inviteToken, expiryTime, rsvpLink));

        String subject = "Official Invitation: " + eventName + " - " + eventDate;
        String body = "<p>Dear " + recipientEmail + ",</p>"
                + "<p>You are invited to <b>" + eventName + "</b> on <b>" + eventDate + "</b> at <b>" + eventTime + "</b> in <b>" + location + "</b>.</p>"
                + "<p>Please <b><a href='" + rsvpLink + "'>RSVP here</a></b> by March 15, 2025.</p>"
                + "<p>Best regards,<br>TechForge Team</p>";

        sendEmail(recipientEmail, subject, body);
    }

    // ✅ Validate Invitation Link
    public boolean validateInvitation(String token) {
        InvitationDetails details = invitationStorage.get(token);
        return details != null && LocalDateTime.now().isBefore(details.getExpiryTime());
    }

    // ✅ Send Email
    private void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Enable HTML formatting

            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("Error occurred while sending email", e);
        }
    }

    // ✅ Generate Random 6-digit OTP
    private String generateOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    // ✅ OTP Details Class
    @Getter
    private static class OTPDetails {
        private final String otp;
        private final LocalDateTime expiryTime;

        public OTPDetails(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }

    // ✅ Invitation Details Class
    @Getter
    private static class InvitationDetails {
        private final String recipientEmail;
        private final String organizationName;
        private final String eventName;
        private final String eventDate;
        private final String eventTime;
        private final String location;
        private final String token;
        private final LocalDateTime expiryTime;
        private final String rsvpLink;

        public InvitationDetails(String recipientEmail, String organizationName, String eventName,
                                 String eventDate, String eventTime, String location,
                                 String token, LocalDateTime expiryTime, String rsvpLink) {
            this.recipientEmail = recipientEmail;
            this.organizationName = organizationName;
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.eventTime = eventTime;
            this.location = location;
            this.token = token;
            this.expiryTime = expiryTime;
            this.rsvpLink = rsvpLink;
        }
    }

}