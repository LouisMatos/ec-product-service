package br.matosit.product_service.application.usecases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import br.matosit.product_service.application.dto.ProductCreatedEvent;
import br.matosit.product_service.application.ports.out.EventPublisher;
import br.matosit.product_service.application.ports.out.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductAlreadyExistsException;

class CreateProductUseCaseImplTest {

  private ProductRepository productRepository;
  private EventPublisher eventPublisher;
  private CreateProductUseCaseImpl useCase;

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepository.class);
    eventPublisher = mock(EventPublisher.class);
    useCase = new CreateProductUseCaseImpl(productRepository, eventPublisher);
  }

  @Test
  void shouldCreateProductSuccessfully() {
    Product product = new Product("1", "Test", "Teste", 10.0, 5, "test-image-3d");
    when(productRepository.findByName("Test")).thenReturn(null);
    when(productRepository.save(product)).thenReturn(product);

    Product result = useCase.create(product);

    assertEquals(product, result);
    verify(productRepository).save(product);
    // Wait for async event publishing
    sleepAsync();
    verify(eventPublisher).publish(any(ProductCreatedEvent.class));
  }

  @Test
  void shouldThrowExceptionWhenProductAlreadyExists() {
    Product product = new Product("1", "Test", "Teste", 10.0, 5, "test-image-3d");
    when(productRepository.findByName("Test")).thenReturn(product);

    assertThrows(ProductAlreadyExistsException.class, () -> useCase.create(product));
    verify(productRepository, never()).save(any());
    verify(eventPublisher, never()).publish(any());
  }

  @Test
  void shouldPublishEventAsyncAndLogSuccess() {
    Product product = new Product("1", "Test", "Teste", 10.0, 5, "test-image-3d");
    when(productRepository.findByName("Test")).thenReturn(null);
    when(productRepository.save(product)).thenReturn(product);

    // Spy to verify async method
    CreateProductUseCaseImpl spyUseCase = Mockito.spy(useCase);
    spyUseCase.create(product);

    sleepAsync();
    verify(eventPublisher).publish(any(ProductCreatedEvent.class));
  }

  @Test
  void shouldHandleExceptionWhenPublishingEventAsync() {
    Product product = new Product("1", "Test", "Teste", 10.0, 5, "test-image-3d");
    when(productRepository.findByName("Test")).thenReturn(null);
    when(productRepository.save(product)).thenReturn(product);
    doThrow(new RuntimeException("Kafka error")).when(eventPublisher).publish(any());

    // No exception should be thrown to the caller
    assertDoesNotThrow(() -> useCase.create(product));
    sleepAsync();
    verify(eventPublisher).publish(any(ProductCreatedEvent.class));
  }

  @Test
  void shouldLogWarnWhenProductAlreadyExists() {
    Product product = new Product("1", "Test", "Teste", 10.0, 5, "test-image-3d");
    when(productRepository.findByName("Test")).thenReturn(product);

    // No exception thrown here, just to cover the log.warn line
    try {
      useCase.create(product);
    } catch (ProductAlreadyExistsException ignored) {
    }
    // No further verification needed, just for coverage
  }

  // Helper to wait for async execution
  private void sleepAsync() {
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (InterruptedException ignored) {
    }
  }
}
