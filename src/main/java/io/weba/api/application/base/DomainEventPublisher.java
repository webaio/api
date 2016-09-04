package io.weba.api.application.base;

public interface DomainEventPublisher {
    void publish(DomainEvent domainEvent);
}
