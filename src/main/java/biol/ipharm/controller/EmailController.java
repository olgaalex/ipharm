package biol.ipharm.controller;

import biol.ipharm.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Olga
 */
@Controller
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(value = "/send-email", method = RequestMethod.POST)
    public String sendEmail(
            @RequestParam("senderEmail") String senderEmail,
            @RequestParam("senderName") String senderName,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message) {
        emailService.sendEmailFromContactForm(senderEmail, senderName, subject, message);
        return "redirect:/letter-was-sent";
    }

    @RequestMapping(value = "/letter-was-sent", method = RequestMethod.GET)
    public String showLetterWasSentPage() {
        return "letter-was-sent";
    }
}
