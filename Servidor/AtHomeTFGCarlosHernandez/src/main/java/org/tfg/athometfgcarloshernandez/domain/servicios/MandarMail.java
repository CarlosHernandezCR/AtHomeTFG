package org.tfg.athometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.tfg.athometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.athometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;

@Log4j2
@Service
@RequiredArgsConstructor
public class MandarMail {
    @Value(Constantes.EMAIL)
    private String correo;

    private final JavaMailSender mailSender;

    public void generateAndSendEmail(String to,String msg, String subject){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(msg);
            mailMessage.setFrom(correo);
            mailSender.send(mailMessage);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new CustomedException(ConstantesError.ERROR_EMAIL);
        }
    }
}