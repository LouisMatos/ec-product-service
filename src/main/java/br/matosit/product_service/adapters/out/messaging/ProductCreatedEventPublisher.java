package br.matosit.product_service.adapters.out.messaging;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.matosit.product_service.application.ports.out.EventPublisher;
import br.matosit.product_service.config.ApplicationProperties;

@Service
public class ProductCreatedEventPublisher implements EventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final ApplicationProperties applicationProperties;
  private static final Logger log = LoggerFactory.getLogger(ProductCreatedEventPublisher.class);

  public ProductCreatedEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
      ApplicationProperties applicationProperties) {
    this.kafkaTemplate = kafkaTemplate;
    this.applicationProperties = applicationProperties;
  }

  @Override
  public void publish(Object event) {
    CompletableFuture.runAsync(() -> {
      try {
        log.info("Publicando evento no tópico {}", applicationProperties.getTopic());
        kafkaTemplate.send(applicationProperties.getTopic(), event);
      } catch (Exception ex) {
        log.error("Erro ao publicar evento no Kafka: {}", ex.getMessage(), ex);
      }
    });
  }

}
