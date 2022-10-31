package com.example.orchestrator.kafka;

import com.example.orchestrator.model.JsonReview;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@NoArgsConstructor
@Component
public class MessageProducerReview {
    private KafkaTemplate<String, JsonReview> kafkaTemplate;

    @Autowired
    public MessageProducerReview(KafkaTemplate<String, JsonReview> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(JsonReview review, String topicName) {
        ListenableFuture<SendResult<String, JsonReview>> future = kafkaTemplate.send(topicName,review);

        future.addCallback(new ListenableFutureCallback<SendResult<String, JsonReview>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Unable to send message = {} dut to: {}", review, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, JsonReview> stringDataSendResult) {
                log.info("Message sent successfully with offset = {}", stringDataSendResult.getRecordMetadata().offset());
            }
        });
    }
}
