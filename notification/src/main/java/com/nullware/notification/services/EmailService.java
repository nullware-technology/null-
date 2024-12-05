package com.nullware.notification.services;

import com.nullware.notification.dtos.EmailDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  @Value(value = "${spring.mail.username}")
  private String emailFrom;

  public void sendEmail(EmailDTO emailDTO) {
    try {
      MimeMessage email = mailSender.createMimeMessage();
      MimeMessageHelper messageHelper = new MimeMessageHelper(email, true);

      messageHelper.setFrom("Null+ <MS_xQAHWp@trial-3vz9dlezon1lkj50.mlsender.net>");
      messageHelper.setTo(emailDTO.emailTo());
      messageHelper.setSubject(emailDTO.subject());

      messageHelper.setText(emailDTO.text(), true);
      mailSender.send(email);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
