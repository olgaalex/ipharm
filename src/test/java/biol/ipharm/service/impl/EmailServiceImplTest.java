package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.Customer;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.order.BasketSessionScopedBean;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;

/**
 *
 * @author Olga
 */
public class EmailServiceImplTest extends SampleBaseTestCase {

    private EmailServiceImpl emailServiceImpl;

    @Mock
    private JavaMailSender mockMailSender;
    @Mock
    private TemplateEngine mockTemplateEngine;

    @Before
    public void setUp() {
        emailServiceImpl = spy(new EmailServiceImpl(mockMailSender, mockTemplateEngine));
    }

    @Test
    public void sendEmailFromContactForm() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        String subject = "subject";
        String message = "message";
        String htmlContent = "htmlContent";
        String templateName = "user-feedback.html";
        doReturn(htmlContent).when(emailServiceImpl).getHtmlContent(matches(templateName), anyMapOf(String.class, Object.class));
        doReturn(true).when(emailServiceImpl).sendEmail(any(EmailContent.class));

        assertTrue(emailServiceImpl.sendEmailFromContactForm(senderEmail, senderName, subject, message));
    }

    @Test
    public void sendOrderConfirmationEmail() {
        String customerEmail = "customer@gmail.com";
        Customer customer = new Customer();
        customer.setEmail(customerEmail);
        TheOrder order = new TheOrder();
        order.setCustomer(customer);
        BasketSessionScopedBean basket = mock(BasketSessionScopedBean.class);
        when(basket.getOrder()).thenReturn(order);
        String htmlContent = "html <b>content</b>";
        doReturn(htmlContent).when(emailServiceImpl).getHtmlContent(matches("order-is-accepted.html"), anyMapOf(String.class, Object.class));
        doReturn(true).when(emailServiceImpl).sendEmail(any(EmailContent.class));

        assertTrue(emailServiceImpl.sendOrderConfirmationEmail(basket));
    }

    @Test
    public void sendEmail_SomethingWentWrong_False() {
        EmailContent emailContent = getTestEmailContent();
        doThrow(new MailSendException("Cannot send email")).when(mockMailSender).send(any(MimeMessagePreparator.class));

        assertFalse(emailServiceImpl.sendEmail(emailContent));
    }

    private static EmailContent getTestEmailContent() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com");
        String subject = "subject";
        String htmlContent = "html <b>content</b>";
        return new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test
    public void sendEmail_EverythingIsAllright_True() {
        EmailContent emailContent = getTestEmailContent();

        assertTrue(emailServiceImpl.sendEmail(emailContent));
    }

    @Test
    public void populateMessageByDataFromEmailContent() throws MessagingException, UnsupportedEncodingException, IOException {
        // Prepare MimeMessageHelper instance
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        // Prepare EmailContent data
        List<String> to = new ArrayList<>(Arrays.asList("first@to.com", "second@to.com"));
        InternetAddress[] toAsArrayOfInternetAddresses = {new InternetAddress("first@to.com"), new InternetAddress("second@to.com")};
        InternetAddress[] ccAsArrayOfInternetAddresses = {new InternetAddress("first@cc.com"), new InternetAddress("second@cc.com")};
        InternetAddress[] bccAsArrayOfInternetAddresses = {new InternetAddress("first@bcc.com"), new InternetAddress("second@bcc.com")};
        EmailContent emailContent = new EmailContent.EmailContentBuilder("sender@gmail.com", "Vasya", to, "subject", "htmlContent")
                .setCc(Arrays.asList("first@cc.com", "second@cc.com"))
                .setBcc(Arrays.asList("first@bcc.com", "second@bcc.com"))
                .build();

        emailServiceImpl.populateMessageByDataFromEmailContent(messageHelper, emailContent);

        // Required email message data
        assertEquals(mimeMessage.getFrom()[0], new InternetAddress(emailContent.getSenderEmail(), emailContent.getSenderName()));
        assertEquals(mimeMessage.getReplyTo()[0], new InternetAddress(emailContent.getSenderEmail()));
        assertEquals(mimeMessage.getRecipients(Message.RecipientType.TO).length, toAsArrayOfInternetAddresses.length);
        assertArrayEquals(mimeMessage.getRecipients(Message.RecipientType.TO), toAsArrayOfInternetAddresses);
        assertEquals(mimeMessage.getSubject(), emailContent.getSubject());
        assertEquals(mimeMessage.getContent(), emailContent.getHtmlContent());

        // Optional email message data
        assertEquals(mimeMessage.getRecipients(Message.RecipientType.CC).length, ccAsArrayOfInternetAddresses.length);
        assertArrayEquals(mimeMessage.getRecipients(Message.RecipientType.CC), ccAsArrayOfInternetAddresses);
        assertEquals(mimeMessage.getRecipients(Message.RecipientType.BCC).length, bccAsArrayOfInternetAddresses.length);
        assertArrayEquals(mimeMessage.getRecipients(Message.RecipientType.BCC), bccAsArrayOfInternetAddresses);
    }
}
