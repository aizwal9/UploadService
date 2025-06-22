package com.aizwal.worker.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@Slf4j
public class DocumentSubscriber {

    @Resource
    private PubSubTemplate pubSubTemplate;

    @Resource
    DocumentProcessor processor;

    @Value("${spring.cloud.gcp.pubsub.subscription}")
    private String subscription;

    @Autowired
    @Lazy
    public DocumentSubscriber(PubSubTemplate pubSubTemplate, DocumentProcessor processor) {
        this.pubSubTemplate = pubSubTemplate;
        this.processor = processor;
    }

    @PostConstruct
    public void subscribe() {
        pubSubTemplate.subscribe(subscription, message -> {
            String payload = message.getPubsubMessage().getData().toStringUtf8();
            log.info("Message received: {}", payload);
            processor.processMessage(payload);
            message.ack();
        });
    }
}
