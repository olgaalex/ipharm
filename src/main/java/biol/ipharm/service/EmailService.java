package biol.ipharm.service;

import biol.ipharm.order.BasketSessionScopedBean;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface EmailService {

    boolean sendEmailFromContactForm(String senderEmail, String senderName, String subject, String message);

    boolean sendOrderConfirmationEmail(BasketSessionScopedBean basketSessionScopedBean);
}
