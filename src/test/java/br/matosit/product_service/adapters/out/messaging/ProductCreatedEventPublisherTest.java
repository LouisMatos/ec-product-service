package br.matosit.product_service.adapters.out.messaging;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import br.matosit.product_service.config.ApplicationProperties;

class ProductCreatedEventPublisherTest {

  private KafkaTemplate<String, Object> kafkaTemplate;
  private ApplicationProperties applicationProperties;
  private ProductCreatedEventPublisher publisher;


  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    kafkaTemplate = mock(KafkaTemplate.class);
    applicationProperties = mock(ApplicationProperties.class);
    when(applicationProperties.getTopic()).thenReturn("test-topic");
    publisher = new ProductCreatedEventPublisher(kafkaTemplate, applicationProperties);
  }

  @Test
  void shouldPublishEventSuccessfully() throws Exception {
    Object event = new Object();
    publisher.publish(event);

    // Wait for async execution
    TimeUnit.MILLISECONDS.sleep(100);

    verify(kafkaTemplate, times(1)).send("test-topic", event);
  }

  @Test
  void shouldHandleExceptionWhenKafkaSendFails() throws Exception {
    Object event = new Object();
    doThrow(new RuntimeException("Kafka error")).when(kafkaTemplate).send(anyString(), any());

    assertDoesNotThrow(() -> publisher.publish(event));
    TimeUnit.MILLISECONDS.sleep(100);
    verify(kafkaTemplate, times(1)).send("test-topic", event);
  }

  @Test
  void shouldHandleExceptionWhenGetTopicFails() throws Exception {
    Object event = new Object();
    when(applicationProperties.getTopic()).thenThrow(new RuntimeException("Config error"));

    assertDoesNotThrow(() -> publisher.publish(event));
    TimeUnit.MILLISECONDS.sleep(100);
    verify(kafkaTemplate, never()).send(anyString(), any());
  }
}
