package biol.ipharm.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class EmailContentTest {

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_SenderEmailIsNotValidEmail_IllegalArgumentException() {
        String senderEmail = "WrongEmail";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com");
        String subject = "subject";
        String htmlContent = "html <b>content</b>";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_SenderNameIsNull_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = null;
        List<String> to = Arrays.asList("to@gmail.com");
        String subject = "subject";
        String htmlContent = "html <b>content</b>";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_SenderNameIsEmpty_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "  ";
        List<String> to = Arrays.asList("to@gmail.com");
        String subject = "subject";
        String htmlContent = "html <b>content</b>";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_ToListIsNull_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = null;
        String subject = "subject";
        String htmlContent = "html <b>content</b>";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_ToListIsEmpty_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Collections.<String>emptyList();
        String subject = "subject";
        String htmlContent = "html <b>content</b>";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_ToListContainsNotValidEmail_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com", "wrongEmail");
        String subject = "subject";
        String htmlContent = "html <b>content</b>";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_SubjectIsNull_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com");
        String subject = null;
        String htmlContent = "html <b>content</b>";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_SubjectIsEmpty_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com");
        String subject = "  ";
        String htmlContent = "html <b>content</b>";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_HtmlContentIsNull_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com");
        String subject = "subject";
        String htmlContent = null;

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_HtmlContentIsEmpty_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com");
        String subject = "subject";
        String htmlContent = "  ";

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_CcListContainsNotValidEmail_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com", "wrongEmail");
        String subject = "subject";
        String htmlContent = "html <b>content</b>";
        List<String> cc = Arrays.asList("wrongEmail");

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).setCc(cc).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void emailContentBuilder_BccListContainsNotValidEmail_IllegalArgumentException() {
        String senderEmail = "sender@gmail.com";
        String senderName = "Vasya";
        List<String> to = Arrays.asList("to@gmail.com", "wrongEmail");
        String subject = "subject";
        String htmlContent = "html <b>content</b>";
        List<String> bcc = Arrays.asList("wrongEmail", "bcc@gmail.com");

        new EmailContent.EmailContentBuilder(senderEmail, senderName, to, subject, htmlContent).setBcc(bcc).build();
    }
}
