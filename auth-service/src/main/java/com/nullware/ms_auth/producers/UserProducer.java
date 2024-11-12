package com.nullware.ms_auth.producers;

import com.nullware.ms_auth.dtos.RecoverPasswordDTO;
import com.nullware.ms_auth.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Produces messages for user registration and password recovery.
 *
 * @see #publishRecoverPasswordMessageEmail(User, String)
 */
@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    /**
     * The routing key for the recover password queue.
     */
    @Value(value = "${broker.queue.recover.password}")
    private String routingKeyRecoverPassword;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publishes a password recovery message to the email queue.
     *
     * @param user     the user to send the password recovery message to
     * @param password the new password
     */
    public void publishRecoverPasswordMessageEmail(User user, String password) {

        var emailDTO = new RecoverPasswordDTO(
                user.getId(),
                user.getEmail(),
                "Redefinir Senha",
                "Sua nova senha é: " + password,
                password
        );
        rabbitTemplate.convertAndSend("", routingKeyRecoverPassword, emailDTO);
    }
}
