package br.matosit.product_service.adapters.in.rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import br.matosit.product_service.application.ports.in.DeleteProductUseCase;

class DeleteProductControllerTest {

  private DeleteProductUseCase deleteProductUseCase;
  private DeleteProductController controller;

  @BeforeEach
  void setUp() {
    deleteProductUseCase = mock(DeleteProductUseCase.class);
    controller = new DeleteProductController(deleteProductUseCase);
  }

  @Test
  void deleteProduct_shouldReturnNoContent_whenSuccess() {
    String id = "123";
    ResponseEntity<Void> response = controller.deleteProduct(id);
    assertEquals("204 NO_CONTENT", response.getStatusCode().toString());
    assertNull(response.getBody());
    verify(deleteProductUseCase, times(1)).delete(id);
  }

  @Test
  void deleteProduct_shouldThrowException_whenUseCaseThrows() {
    String id = "456";
    doThrow(new RuntimeException("Delete failed")).when(deleteProductUseCase).delete(id);
    assertThrows(RuntimeException.class, () -> controller.deleteProduct(id));
    verify(deleteProductUseCase, times(1)).delete(id);
  }

  @Test
  void deleteProduct_shouldAllowNullId_andDelegateToUseCase() {
    // Depending on your business logic, this may throw or not.
    // Here, we just verify delegation.
    doNothing().when(deleteProductUseCase).delete(null);
    ResponseEntity<Void> response = controller.deleteProduct(null);
    assertEquals("204 NO_CONTENT", response.getStatusCode().toString());
    verify(deleteProductUseCase, times(1)).delete(null);
  }
}
