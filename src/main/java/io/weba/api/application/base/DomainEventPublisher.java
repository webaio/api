package io.weba.api.application.base;

public interface DomainEventPublisher {
    void publishEvent(DomainEvent domainEvent);
}
