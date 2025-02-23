package org.tfg.athometfgcarloshernandez.domain.servicios;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;

import static org.tfg.athometfgcarloshernandez.common.constantes.Constantes.ERROR_AL_ENVIAR_EMAIL_A;

@Log4j2
@Service
@RequiredArgsConstructor
public class MailService {
    @Value(Constantes.EMAIL)
    private String correo;

    private final JavaMailSender mailSender;

    @Transactional
    public void sendEmail(String to, String htmlContent, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom(correo);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error(ERROR_AL_ENVIAR_EMAIL_A, to, e.getMessage());
            throw new CustomedException(ConstantesError.ERROR_EMAIL);
        }
    }
}