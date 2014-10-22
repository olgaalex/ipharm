package biol.ipharm.service.impl;

import biol.ipharm.order.BasketSessionScopedBean;
import biol.ipharm.service.EmailService;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 *
 * @author Olga
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = Logger.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public boolean sendEmailFromContactForm(String senderEmail, String senderName, String subject, String message) {
        List<String> to = Arrays.asList("ipharmbiol@gmail.com");

        Map<String, Object> contentVariables = new HashMap<>();
        contentVariables.put("senderName", senderName);
        contentVariables.put("message", message);
        String templateName = "user-feedback.html";
        String htmlContent = getHtmlContent(templateName, contentVariables);

        EmailContent emailContent = new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
        boolean isSuccessfullySent = sendEmail(emailContent);
        return isSuccessfullySent;
    }

    String getHtmlContent(String templateName, Map<String, ?> variables) {
        // Prepare the evaluation context
        Context context = new Context(new Locale("ru"), variables);
        return templateEngine.process(templateName, context);
    }

    @Override
    public boolean sendOrderConfirmationEmail(BasketSessionScopedBean basketSessionScopedBean) {
        String senderEmail = "ipharmbiol@gmail.com";
        String senderName = "Ольга";
        List<String> to = Arrays.asList(basketSessionScopedBean.getOrder().getCustomer().getEmail());
        List<String> bcc = Arrays.asList("o.gorbatiuk@outlook.com");
        String subject = "Вы сделали заказ на ipharm-biol.rhcloud.com";

        Map<String, Object> contentVariables = new HashMap<>();
        contentVariables.put("basketSessionScopedBean", basketSessionScopedBean);
        String templateName = "order-is-accepted.html";
        String htmlContent = getHtmlContent(templateName, contentVariables);

        EmailContent emailContent = new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).setBcc(bcc).build();
        boolean isSuccessfullySent = sendEmail(emailContent);
        return isSuccessfullySent;
    }

    boolean sendEmail(final EmailContent emailContent) {
        boolean isSuccessfullySent = true;

        try {
            // See http://docs.spring.io/spring/docs/current/javadoc-api/index.html?org/springframework/mail/javamail/MimeMessageHelper.html
            mailSender.send(new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");

                    populateMessageByDataFromEmailContent(message, emailContent);
                }

            });
        } catch (MailException ex) {
            LOG.error("Failure when send email: ", ex);
            isSuccessfullySent = false;
            return isSuccessfullySent;
        }
        return isSuccessfullySent;
    }

    void populateMessageByDataFromEmailContent(MimeMessageHelper message, EmailContent emailContent) throws MessagingException, UnsupportedEncodingException {
        // Set required parameters
        message.setFrom(emailContent.getSenderEmail(), emailContent.getSenderName());
        message.setReplyTo(emailContent.getSenderEmail());
        for (String to : emailContent.getTo()) {
            message.addTo(to);
        }
        message.setSubject(emailContent.getSubject());
        message.setText(emailContent.getHtmlContent(), true);

        // Set optional parameters
        for (String cc : emailContent.getCc()) {
            message.addCc(cc);
        }
        for (String bcc : emailContent.getBcc()) {
            message.addBcc(bcc);
        }
    }
}
