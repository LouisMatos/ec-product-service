
package br.matosit.product_service.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductAlreadyExistsException;

class CreateProductUseCaseTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private CreateProductUseCase createProductUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateProductWhenItDoesNotExist() {
    Product product = new Product("New Product", null, null, null, null);
    when(productRepository.findByName(product.getName())).thenReturn(null);
    when(productRepository.save(product)).thenReturn(product);

    Product createdProduct = createProductUseCase.execute(product);

    assertNotNull(createdProduct);
    assertEquals(product.getName(), createdProduct.getName());
    verify(productRepository).findByName(product.getName());
    verify(productRepository).save(product);
  }

  @Test
  void shouldThrowExceptionWhenProductAlreadyExists() {
    Product product = new Product("Existing Product", null, null, null, null);
    when(productRepository.findByName(product.getName())).thenReturn(product);

    assertThrows(ProductAlreadyExistsException.class, () -> createProductUseCase.execute(product));

    verify(productRepository).findByName(product.getName());
    verify(productRepository, never()).save(product);
  }
}
