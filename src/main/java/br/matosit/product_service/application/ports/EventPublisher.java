package br.matosit.product_service.application.ports;

/**
 * Interface for publishing domain events.
 */
public interface EventPublisher {
  void publish(Object event);
}
