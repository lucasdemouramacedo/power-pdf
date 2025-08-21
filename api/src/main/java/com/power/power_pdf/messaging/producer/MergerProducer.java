package com.power.power_pdf.messaging.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import static com.power.power_pdf.config.RabbitMQConfig.EXCHANGE_NAME;
import static com.power.power_pdf.config.RabbitMQConfig.ROUTING_KEY;

@Slf4j
@Service
public class MergerProducer {

    private final RabbitTemplate rabbitTemplate;

    public MergerProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        log.info("Info (RabbitMQ - MergerProducer) - Sending message: )", message);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
    }
}
