package com.aizwal.worker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.DefaultPublisherFactory;
import org.springframework.cloud.gcp.pubsub.support.DefaultSubscriberFactory;
import org.springframework.cloud.gcp.pubsub.support.PublisherFactory;
import org.springframework.cloud.gcp.pubsub.support.SubscriberFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubConfig {

    @Value("${spring.cloud.gcp.project-id}")
    private String gcpProjectId;

    @Bean
    public PublisherFactory publisherFactory() {
        return new DefaultPublisherFactory(() -> gcpProjectId);
    }

    @Bean
    public SubscriberFactory subscriberFactory() {
        return new DefaultSubscriberFactory(() -> gcpProjectId);
    }

    @Bean
    public PubSubTemplate pubSubTemplate(PublisherFactory publisherFactory, SubscriberFactory subscriberFactory) {
        return new PubSubTemplate(publisherFactory, subscriberFactory);
    }
}
