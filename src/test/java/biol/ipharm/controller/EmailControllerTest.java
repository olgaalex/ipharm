package biol.ipharm.controller;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.service.EmailService;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Olga
 */
public class EmailControllerTest extends SampleBaseTestCase {

    private EmailController emailController;

    @Mock
    EmailService mockEmailService;

    @Before
    public void setUp() {
        emailController = new EmailController(mockEmailService);
    }

    @Test
    public void sendEmail() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        String subject = "Subject";
        String message = "Message";

        String actualView = emailController.sendEmail(senderEmail, senderName, subject, message);

        verify(mockEmailService).sendEmailFromContactForm(senderEmail, senderName, subject, message);
        assertEquals("redirect:/letter-was-sent", actualView);
    }

    @Test
    public void showLetterWasSentPage() {
        assertEquals("letter-was-sent", emailController.showLetterWasSentPage());
    }
}
