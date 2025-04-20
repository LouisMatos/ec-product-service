package br.matosit.product_service.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import br.matosit.product_service.application.ports.EventPublisher;
import br.matosit.product_service.infrastructure.config.ApplicationProperties;

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
    log.info("Iniciando publicação do evento no tópico {}", applicationProperties.getTopic());
    kafkaTemplate.send(applicationProperties.getTopic(), event).whenComplete((result, ex) -> {
      if (ex == null) {
        log.info("Evento publicado com sucesso no tópico {}. Detalhes: [partition={}, offset={}]",
            applicationProperties.getTopic(), result.getRecordMetadata().partition(),
            result.getRecordMetadata().offset());
      } else {
        log.error("Falha ao publicar evento no tópico {}. Erro: {}",
            applicationProperties.getTopic(), ex.getMessage());
      }
    });
  }
}
