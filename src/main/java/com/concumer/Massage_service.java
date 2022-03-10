package com.concumer;

import com.dto.Massage_dto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.rest.dto.message.MessageCorrelationResultDto;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.stereotype.Service;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class Massage_service {
    private final RuntimeService runtimeService;

    public MessageCorrelationResult correlateMessage(Massage_dto msg, String messageName) {
        try {
            log.info("Consuming message {}", messageName);

            MessageCorrelationBuilder messageCorrelationBuilder = runtimeService.createMessageCorrelation(messageName);
            if (msg.getVariables() != null) {
                messageCorrelationBuilder.setVariable("Variables",msg.getVariables());
            }
            MessageCorrelationResult messageResult = messageCorrelationBuilder.correlateWithResult();

            String messageResultJson = new ObjectMapper().writeValueAsString(MessageCorrelationResultDto.fromMessageCorrelationResult(messageResult));
            log.info("Correlation successful. Process Instance Id: {}", messageResultJson);


            return messageResult;
        } catch (MismatchingMessageCorrelationException e) {
            log.error("Issue when correlating the message: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unknown issue occurred", e);
        }
        return null;
    }
}