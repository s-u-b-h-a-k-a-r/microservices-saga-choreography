package com.pattern.paymentservice.listeners;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.pattern.paymentservice.converters.Converter;
import com.pattern.paymentservice.events.BilledOrderEvent;

@Log4j2
@Component
public class BilledOrderEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;
    private final String queueBilledOrderName;

    public BilledOrderEventListener(RabbitTemplate rabbitTemplate,
                                    Converter converter,
                                    @Value("${queue.billed-order}") String queueBilledOrderName) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.queueBilledOrderName = queueBilledOrderName;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBilledOrderEvent(BilledOrderEvent event) {
        log.debug("Sending billed order event to {}, event: {}", queueBilledOrderName, event);
        rabbitTemplate.convertAndSend(queueBilledOrderName, converter.toJSON(event));
    }

}
