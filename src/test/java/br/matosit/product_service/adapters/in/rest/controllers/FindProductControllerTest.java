package br.matosit.product_service.adapters.in.rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import br.matosit.product_service.adapters.in.rest.mappers.ProductMapper;
import br.matosit.product_service.adapters.in.rest.responses.ProductResponse;
import br.matosit.product_service.application.ports.in.FindProductUseCase;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

class FindProductControllerTest {

  private FindProductUseCase findProductUseCase;
  private FindProductController controller;

  @BeforeEach
  void setUp() {
    findProductUseCase = mock(FindProductUseCase.class);
    controller = new FindProductController(findProductUseCase);
  }

  @Test
  void findProduct_shouldReturnProductResponse_whenProductExists() {
    String id = "1";
    Product product = mock(Product.class);
    ProductResponse response = mock(ProductResponse.class);

    when(findProductUseCase.find(id)).thenReturn(product);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class)) {
      productMapperMock.when(() -> ProductMapper.toResponse(product)).thenReturn(response);

      ResponseEntity<ProductResponse> result = controller.getProduct(id);

      assertEquals("200 OK", result.getStatusCode().toString());
      assertEquals(response, result.getBody());
    }
  }

  @Test
  void findProduct_shouldThrowException_whenProductIsNull() {
    String id = "2";
    when(findProductUseCase.find(id)).thenReturn(null);


    assertThrows(RuntimeException.class, () -> controller.getProduct(id));
  }

  @Test
  void findProduct_shouldThrowException_whenUseCaseThrows() {
    String id = "3";
    when(findProductUseCase.find(id)).thenThrow(new RuntimeException("DB error"));

    assertThrows(RuntimeException.class, () -> controller.getProduct(id));
  }

  @Test
  void findProduct_shouldThrowException_whenIdIsNotFound() {
    String nonExistentId = "999";

    when(findProductUseCase.find(nonExistentId))
        .thenThrow(new ProductNotFoundException(nonExistentId));


    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> controller.getProduct(nonExistentId));
    assertEquals("Produto com id "+nonExistentId+" não encontrado", exception.getMessage());
  }


}
