package com.nullware.notification.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


@Configuration
public class RabbitMQConfig {

  @Value("${broker.queue.send.email}")
  private String queueSendEmail;

  @Bean
  public Queue sendEmailQueue() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-message-ttl", 600000); // TTL of 10 minutes (in milliseconds)
    args.put("x-queue-type", "quorum"); // Quorum type, high availability
    return new Queue(queueSendEmail, true, false, false, args);
  }

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();

    // Configuring exponential backoff policy
    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(30000); // 30 seconds initial delay
    backOffPolicy.setMultiplier(4.0); // Exponential multiplier
    backOffPolicy.setMaxInterval(10 * 60 * 1000); // Maximum interval of 10 minutes
    retryTemplate.setBackOffPolicy(backOffPolicy);

    // Configuring simple retry policy with a max of 5 attempts
    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(5);
    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    return new Jackson2JsonMessageConverter(objectMapper);
  }
}