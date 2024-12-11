package org.tfg.inhometfgcarloshernandez.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesSpring.*;

@Configuration
public class MailConfig {

    @Value(MAIL_HOST)
    private String mailHost;
    @Value(MAIL_PORT)
    private int mailPort;
    @Value(MAIL_USERNAME)
    private String mailUsername;
    @Value(MAIL_PASSWORD)
    private String mailPassword;
    @Value(MAIL_PROPERTY_AUTH)
    private String mailPropertyAuth;
    @Value(MAIL_PROPERTY_TRANSPORT_PROTOCOL)
    private String mailPropertyTransportProtocol;
    @Value(MAIL_PROPERTY_STARTTLS)
    private String mailPropertyStarttls;
    @Value(MAIL_PROPERTY_DEBUG)
    private String mailPropertyDebug;

    @Bean(name = BEAN_NAME)
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put(MAIL_TRANSPORT_PROTOCOL, mailPropertyTransportProtocol);
        props.put(MAIL_SMTP_AUTH, mailPropertyAuth);
        props.put(MAIL_SMTP_STARTTLS_ENABLE, mailPropertyStarttls);
        props.put(MAIL_DEBUG, mailPropertyDebug);

        return mailSender;
    }

}