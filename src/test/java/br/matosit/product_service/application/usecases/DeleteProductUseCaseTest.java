package br.matosit.product_service.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

class DeleteProductUseCaseTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private DeleteProductUseCase deleteProductUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldDeleteProductSuccessfully() {
    String productId = "1";
    when(productRepository.existsById(productId)).thenReturn(true);

    deleteProductUseCase.execute(productId);

    verify(productRepository).existsById(productId);
    verify(productRepository).deleteById(productId);
  }

  @Test
  void shouldThrowExceptionWhenProductNotFound() {
    String productId = "2";
    when(productRepository.existsById(productId)).thenReturn(false);

    assertThrows(ProductNotFoundException.class, () -> deleteProductUseCase.execute(productId));

    verify(productRepository).existsById(productId);
    verify(productRepository, never()).deleteById(anyString());
  }

  @Test
  void shouldHandleErrorWhenDeletingProduct() {
    String productId = "3";
    when(productRepository.existsById(productId)).thenReturn(true);
    doThrow(new RuntimeException("Database error")).when(productRepository).deleteById(productId);

    assertThrows(RuntimeException.class, () -> deleteProductUseCase.execute(productId));

    verify(productRepository).existsById(productId);
    verify(productRepository).deleteById(productId);
  }

  @Test
  void shouldCallRepositoryMethodsOnlyOnce() {
    String productId = "4";
    when(productRepository.existsById(productId)).thenReturn(true);

    deleteProductUseCase.execute(productId);

    verify(productRepository, times(1)).existsById(productId);
    verify(productRepository, times(1)).deleteById(productId);
    verifyNoMoreInteractions(productRepository);
  }
}
