package com.concumer;


import com.dto.Massage_dto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Massage_consumer {

    private final Massage_service messageService;
    private final static String MESSAGE_START = "MessageKafkaDemo";

    @KafkaListener(topics = "send_mail_topic", containerFactory = "Consumer_config")
    public void consumeJson(Massage_dto msg) {
        messageService.correlateMessage(msg, MESSAGE_START);
        //System.out.println("Consumed JSON Message: " + msg);
    }
}
