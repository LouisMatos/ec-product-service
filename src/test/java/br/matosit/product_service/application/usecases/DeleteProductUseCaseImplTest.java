package br.matosit.product_service.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.matosit.product_service.application.ports.out.ProductRepository;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteProductUseCaseImplTest {

  private ProductRepository productRepository;
  private DeleteProductUseCaseImpl useCase;

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepository.class);
    useCase = new DeleteProductUseCaseImpl(productRepository);
  }

  @Test
  void shouldDeleteProductSuccessfully() {
    String id = "123";
    when(productRepository.existsById(id)).thenReturn(true);
    // No exception thrown by deleteById
    doNothing().when(productRepository).deleteById(id);

    assertDoesNotThrow(() -> useCase.delete(id));
    verify(productRepository).deleteById(id);
  }

  @Test
  void shouldThrowExceptionWhenProductNotFound() {
    String id = "not-found";
    when(productRepository.existsById(id)).thenReturn(false);

    assertThrows(ProductNotFoundException.class, () -> useCase.delete(id));
    verify(productRepository, never()).deleteById(any());
  }

  @Test
  void shouldThrowExceptionWhenDeleteFails() {
    String id = "error";
    when(productRepository.existsById(id)).thenReturn(true);
    doThrow(new RuntimeException("DB error")).when(productRepository).deleteById(id);

    assertThrows(ProductNotFoundException.class, () -> useCase.delete(id));
    verify(productRepository).deleteById(id);
  }

  @Test
  void shouldReturnFalseWhenDeleteProductThrowsException() {
    // This test is for coverage of the private deleteProduct method's catch block
    String id = "fail";
    when(productRepository.existsById(id)).thenReturn(true);
    doThrow(new RuntimeException("fail")).when(productRepository).deleteById(id);

    assertThrows(ProductNotFoundException.class, () -> useCase.delete(id));
  }
}
