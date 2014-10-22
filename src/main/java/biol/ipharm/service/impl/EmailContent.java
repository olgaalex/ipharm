package biol.ipharm.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notEmpty;

/**
 *
 * @author Olga
 */
class EmailContent {

    // Формат сообщений электронной почты: http://rfc2.ru/5322.rfc/print
    // То же, английский оригинал: https://tools.ietf.org/html/rfc5322
    private final String senderEmail;
    private final String senderName;
    private final List<String> to;
    private final String subject;
    private final String htmlContent;
    private final List<String> cc; // Optional
    private final List<String> bcc; // Optional

    // Использование паттерна Builder в случае, когда мы сталкиваемся с конструктором с многими параметрами
    // See http://habrahabr.ru/post/86252/ or 'Effective Java' by Joshua Bloch, 2nd edition, Item 2.
    public static class EmailContentBuilder {

        // Required parameters
        private final String senderEmail;
        private final String senderName;
        private final List<String> to;
        private final String subject;
        private final String htmlContent;

        // Optional parameters - initialized to default values
        private List<String> cc = Collections.<String>emptyList();
        private List<String> bcc = Collections.<String>emptyList();

        public EmailContentBuilder(
                String senderEmail,
                String senderName,
                List<String> to,
                String subject,
                String htmlContent) {
            validateInputData(senderEmail, senderName, to, subject, htmlContent);

            this.senderEmail = senderEmail;
            this.senderName = senderName;
            this.to = to;
            this.subject = subject;
            this.htmlContent = htmlContent;
        }

        private void validateInputData(String senderEmail, String senderName, List<String> to, String subject, String htmlContent) {
            containsEmailsOnly(Arrays.asList(senderEmail));
            hasText(senderName, "Имя отправителя не должно быть пустым.");
            notEmpty(to, "Список получателей должен содержать хотя бы один электронный адрес.");
            containsEmailsOnly(to);
            hasText(subject, "Тема сообщения не должна быть пустой.");
            hasText(htmlContent, "Сообщение не должно быть пустым.");
        }

        private void containsEmailsOnly(List<String> emails) {
            String emailPattern = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
            for (String email : emails) {
                isTrue(email.matches(emailPattern), "Электронный адрес [" + email + "] не является валидным электронным адресом.");
            }
        }

        public EmailContentBuilder setCc(List<String> cc) {
            notEmpty(cc, "Список получателей в копии должен содержать хотя бы один электронный адрес.");
            containsEmailsOnly(cc);
            this.cc = cc;
            return this;
        }

        public EmailContentBuilder setBcc(List<String> bcc) {
            notEmpty(bcc, "Список получателей в скрытой копии должен содержать хотя бы один электронный адрес.");
            containsEmailsOnly(bcc);
            this.bcc = bcc;
            return this;
        }

        public EmailContent build() {
            return new EmailContent(this);
        }
    }

    // Вызывается билдером EmailContentBuilder, объявленным в этом же классе.
    private EmailContent(EmailContentBuilder builder) {
        this.senderEmail = builder.senderEmail;
        this.senderName = builder.senderName;
        this.to = builder.to;
        this.subject = builder.subject;
        this.htmlContent = builder.htmlContent;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public List<String> getTo() {
        return to;
    }

    public List<String> getCc() {
        return cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getHtmlContent() {
        return htmlContent;
    }
}
