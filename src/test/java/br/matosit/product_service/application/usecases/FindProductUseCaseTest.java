package br.matosit.product_service.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.matosit.product_service.application.dto.ProductDTO;
import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

class FindProductUseCaseTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private FindProductUseCase findProductUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnProductWhenProductExists() {
    Product product = new Product("1", "Existing Product", "Description", 100.0, 10, "image3D");
    when(productRepository.findById("1")).thenReturn(Optional.of(product));

    ProductDTO foundProduct = findProductUseCase.execute("1");

    assertNotNull(foundProduct);
    assertEquals("1", foundProduct.getId());
    assertEquals("Existing Product", foundProduct.getName());
    assertEquals("Description", foundProduct.getDescription());
    assertEquals(100.0, foundProduct.getPrice());
    verify(productRepository).findById("1");
  }

  @Test
  void shouldThrowExceptionWhenProductDoesNotExist() {
    when(productRepository.findById("2")).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> findProductUseCase.execute("2"));

    verify(productRepository).findById("2");
  }

  @Test
  void shouldMapAllFieldsCorrectly() {
    Product product = new Product("1", "Product Name", "Description", 100.0, 10, "image3D");
    when(productRepository.findById("1")).thenReturn(Optional.of(product));

    ProductDTO foundProduct = findProductUseCase.execute("1");

    assertNotNull(foundProduct);
    assertEquals("1", foundProduct.getId());
    assertEquals("Product Name", foundProduct.getName());
    assertEquals("Description", foundProduct.getDescription());
    assertEquals(100.0, foundProduct.getPrice());
    verify(productRepository).findById("1");
  }

  @Test
  void shouldCallRepositoryOnlyOnce() {
    Product product = new Product("1", "Product Name", "Description", 100.0, 10, "image3D");
    when(productRepository.findById("1")).thenReturn(Optional.of(product));

    findProductUseCase.execute("1");

    verify(productRepository).findById("1");
    verifyNoMoreInteractions(productRepository);
  }
}
