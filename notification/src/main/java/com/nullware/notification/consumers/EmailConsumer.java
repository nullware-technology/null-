package com.nullware.notification.consumers;

import com.nullware.notification.dtos.EmailDTO;
import com.nullware.notification.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

  private final EmailService emailService;

  @RabbitListener(queues = "${broker.queue.send.email}")
  public void listenSendEmailQueue(@Payload EmailDTO emailDTO) {
    emailService.sendEmail(emailDTO);
  }

}
