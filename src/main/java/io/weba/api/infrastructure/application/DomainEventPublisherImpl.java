package io.weba.api.infrastructure.application;

import io.weba.api.application.base.DomainEvent;
import io.weba.api.application.base.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public DomainEventPublisherImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        this.applicationEventPublisher.publishEvent(domainEvent);
    }
}
