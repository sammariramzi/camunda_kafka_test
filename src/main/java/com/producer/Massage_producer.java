package com.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import spinjar.com.minidev.json.JSONObject;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class Massage_producer implements JavaDelegate {
    private final KafkaTemplate kafkaTemplate;
    @Override
    public void execute(DelegateExecution execution) throws IOException {

        JSONObject gg = new JSONObject();
        gg.putAll((JSONObject) execution.getVariable("Variables"));

        kafkaTemplate.send("send_mail_topic_2", gg);


        System.out.println("test valider");

    }



}
