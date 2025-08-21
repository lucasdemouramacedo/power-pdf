package com.power.power_pdf.messaging.consumer;

import com.power.power_pdf.service.MergeRequestService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import static com.power.power_pdf.config.RabbitMQConfig.QUEUE_NAME;

@Slf4j
@Component
public class MergerConsumer {

    private final MergeRequestService mergeRequestService;

    public MergerConsumer(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }
    @SneakyThrows
    @RabbitListener(queues = {QUEUE_NAME})
    public void receiveMessage(String message) {
        mergeRequestService.makePdfsMerge(message);
        log.info("Info (RabbitMQ - MergerConsumer) - Received message: {})", message);
    }
}
