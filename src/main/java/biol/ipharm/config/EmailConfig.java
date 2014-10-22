package biol.ipharm.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author Olga
 */
@Configuration
// About @PropertySource annotation: http://www.jayway.com/2014/02/16/spring-propertysource/
@PropertySource({"classpath:/email/email-config.properties", "classpath:/email/javamail.properties"})
public class EmailConfig {

    @Autowired
    Environment env;

    // JavaMail API FAQ: http://www.oracle.com/technetwork/java/javamail/faq/index.html
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("mail.server.host"));
        mailSender.setPort(Integer.valueOf(env.getProperty("mail.server.port")));
        mailSender.setProtocol(env.getProperty("mail.server.protocol"));
        mailSender.setUsername(env.getProperty("mail.server.username"));
        mailSender.setPassword(env.getProperty("mail.server.password"));
        mailSender.setJavaMailProperties(getJavaMailProperties());
        return mailSender;
    }

    private Properties getJavaMailProperties() {
        Properties javaMailProperties = new Properties();
        // List of mail.smtp properties:
        // https://javamail.java.net/nonav/docs/api/index.html?com/sun/mail/smtp/package-summary.html
        javaMailProperties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.quitwait", env.getProperty("mail.smtp.quitwait"));
        javaMailProperties.put("mail.smtp.ssl.enable", env.getProperty("mail.smtp.ssl.enable"));
        return javaMailProperties;
    }
}
