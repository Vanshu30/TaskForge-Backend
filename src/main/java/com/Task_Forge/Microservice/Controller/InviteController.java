package com.Task_Forge.Microservice.Controller;

import com.Task_Forge.Microservice.MailService.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invite")
public class InviteController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public String sendInvite(@RequestParam String email, @RequestParam String organizationName) {
        String inviteLink = "https://yourapp.com/invite?email=" + email; // Replace with actual logic
        mailService.sendInviteEmail(email, organizationName, inviteLink);
        return "Invitation sent successfully to " + email;
    }
}
