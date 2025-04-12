package br.matosit.product_service.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

class UpdateProductUseCaseTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private UpdateProductUseCase updateProductUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldUpdateProductSuccessfully() {
    String productId = "1";
    Product existingProduct =
        new Product("1", "Old Name", "Old Description", 50.0, 5, "oldImage3D");
    Product updatedProduct =
        new Product("1", "New Name", "New Description", 100.0, 10, "newImage3D");

    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
    when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

    Product result = updateProductUseCase.execute(productId, updatedProduct);

    assertNotNull(result);
    assertEquals("New Name", result.getName());
    assertEquals("New Description", result.getDescription());
    assertEquals(100.0, result.getPrice());
    verify(productRepository).findById(productId);
    verify(productRepository).save(existingProduct);
  }

  @Test
  void shouldThrowExceptionWhenProductNotFound() {
    String productId = "2";
    Product updatedProduct = new Product("2", "Name", "Description", 100.0, 10, "image3D");

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class,
        () -> updateProductUseCase.execute(productId, updatedProduct));

    verify(productRepository).findById(productId);
    verify(productRepository, never()).save(any());
  }

  @Test
  void shouldHandleErrorWhenSavingProduct() {
    String productId = "3";
    Product existingProduct =
        new Product("3", "Old Name", "Old Description", 50.0, 5, "oldImage3D");
    Product updatedProduct =
        new Product("3", "New Name", "New Description", 100.0, 10, "newImage3D");

    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
    when(productRepository.save(existingProduct)).thenThrow(new RuntimeException("Database error"));

    assertThrows(RuntimeException.class,
        () -> updateProductUseCase.execute(productId, updatedProduct));

    verify(productRepository).findById(productId);
    verify(productRepository).save(existingProduct);
  }

  @Test
  void shouldCallRepositoryMethodsOnlyOnce() {
    String productId = "4";
    Product existingProduct =
        new Product("4", "Old Name", "Old Description", 50.0, 5, "oldImage3D");
    Product updatedProduct =
        new Product("4", "New Name", "New Description", 100.0, 10, "newImage3D");

    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
    when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

    updateProductUseCase.execute(productId, updatedProduct);

    verify(productRepository, times(1)).findById(productId);
    verify(productRepository, times(1)).save(existingProduct);
    verifyNoMoreInteractions(productRepository);
  }
}
