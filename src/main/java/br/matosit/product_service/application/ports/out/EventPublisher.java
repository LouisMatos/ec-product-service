package br.matosit.product_service.application.ports.out;

/**
 * Interface for publishing domain events.
 */
public interface EventPublisher {
  void publish(Object event);
}
