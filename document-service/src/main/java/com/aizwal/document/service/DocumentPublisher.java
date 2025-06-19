package com.aizwal.document.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocumentPublisher {

    private final PubSubTemplate pubSubTemplate;
    private final String topicName;

    public DocumentPublisher(PubSubTemplate pubSubTemplate,
                             @Value("${spring.cloud.gcp.pubsub.topic}") String topicName) {
        this.pubSubTemplate = pubSubTemplate;
        this.topicName = topicName;
    }

    public void publishDocId(String docId) {
        pubSubTemplate.publish(topicName, docId);
    }
}
