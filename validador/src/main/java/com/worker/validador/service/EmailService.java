package com.worker.validador.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    Logger log = LogManager.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void notificarClienteCompraComSucesso(String email) {
        /*var msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Compra confirmada");
        msg.setText("Parabens! Sua compra foi aprovada e breve voce recebera seu codigo de rastreio!");
        javaMailSender.send(msg);*/
        log.info("Customer notified of successfully approved purchase!!!!");
    }

}
