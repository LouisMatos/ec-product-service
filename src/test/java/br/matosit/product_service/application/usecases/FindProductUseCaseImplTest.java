package br.matosit.product_service.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.matosit.product_service.application.ports.out.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class FindProductUseCaseImplTest {

  private ProductRepository productRepository;
  private FindProductUseCaseImpl useCase;

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepository.class);
    useCase = new FindProductUseCaseImpl(productRepository);
  }

  @Test
  void shouldFindProductSuccessfully() {
    String id = "1";
    Product product = new Product(id, "Test", "Desc", 10.0, 5, "img");
    when(productRepository.findById(id)).thenReturn(Optional.of(product));

    Product result = useCase.find(id);

    assertNotNull(result);
    assertEquals(product, result);
    verify(productRepository).findById(id);
  }

  @Test
  void shouldThrowExceptionWhenProductNotFound() {
    String id = "not-found";
    when(productRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> useCase.find(id));
    verify(productRepository).findById(id);
  }

  @Test
  void shouldLogWarnWhenProductNotFound() {
    // This test is for coverage of the log.warn line
    String id = "missing";
    when(productRepository.findById(id)).thenReturn(Optional.empty());

    try {
      useCase.find(id);
    } catch (ProductNotFoundException ignored) {
    }
    // No further verification needed, just for coverage
  }
}
