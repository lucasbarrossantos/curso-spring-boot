package com.workercompras.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.workercompras.model.Pedido;
import com.workercompras.service.producer.PedidoProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PedidoProducer pedidoProducer;

    @Value("${outlook.mail-from}")
    private String mailFrom;

    public void notificarCliente(Pedido pedido) throws JsonProcessingException {
        var msg = new SimpleMailMessage();
        msg.setFrom(mailFrom);
        msg.setTo(pedido.getEmail());
        msg.setSubject("Compra recebida");
        msg.setText("Este é um e-mail de confirmação de compra recebida. " +
                "Agora vamos aprovar sua compra e brevemente você receberá um novo e-mail de confirmação." +
                "\nObrigado por comprar com a gente!!");
        // javaMailSender.send(msg);
        log.info("Customer notify successfully!!");

        log.info("Preparing order to send for producer");
        pedidoProducer.sendOrder(pedido);
    }

}
