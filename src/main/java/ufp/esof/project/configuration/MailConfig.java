package ufp.esof.project.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${mailgun.api.key:your-mailgun-api-key}")
    private String mailgunApiKey;

    @Value("${mailgun.domain:your-mailgun-domain}")
    private String mailgunDomain;

    @Value("${mailgun.host:smtp.mailgun.org}")
    private String host;

    @Value("${mailgun.port:587}")
    private int port;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername("postmaster@" + mailgunDomain);
        mailSender.setPassword(mailgunApiKey);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.mailgun.org");

        return mailSender;
    }
}
