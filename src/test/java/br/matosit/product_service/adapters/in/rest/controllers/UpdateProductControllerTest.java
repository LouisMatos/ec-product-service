package br.matosit.product_service.adapters.in.rest.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import br.matosit.product_service.adapters.in.rest.mappers.ProductMapper;
import br.matosit.product_service.adapters.in.rest.requests.UpdateProductRequest;
import br.matosit.product_service.adapters.in.rest.responses.ProductResponse;
import br.matosit.product_service.application.ports.in.UpdateProductUseCase;
import br.matosit.product_service.domain.entities.Product;

class UpdateProductControllerTest {

  private UpdateProductUseCase updateProductUseCase;
  private UpdateProductController controller;

  @BeforeEach
  void setUp() {
    updateProductUseCase = mock(UpdateProductUseCase.class);
    controller = new UpdateProductController(updateProductUseCase);
  }

  @Test
  void updateProduct_shouldReturnOk_whenSuccess() {
    String id = "1";
    UpdateProductRequest request = mock(UpdateProductRequest.class);
    Product product = mock(Product.class);
    Product updatedProduct = mock(Product.class);
    ProductResponse response = mock(ProductResponse.class);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class)) {
      productMapperMock.when(() -> ProductMapper.toDomain(request)).thenReturn(product);
      when(updateProductUseCase.update(id, product)).thenReturn(updatedProduct);
      productMapperMock.when(() -> ProductMapper.toResponse(updatedProduct)).thenReturn(response);

      ResponseEntity<ProductResponse> result = controller.updateProduct(id, request);

      assertEquals("200 OK", result.getStatusCode().toString());
      assertEquals(response, result.getBody());
    }
  }

  @Test
  void updateProduct_shouldThrowException_whenUseCaseThrows() {
    String id = "2";
    UpdateProductRequest request = mock(UpdateProductRequest.class);
    Product product = mock(Product.class);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class)) {
      productMapperMock.when(() -> ProductMapper.toDomain(request)).thenReturn(product);
      when(updateProductUseCase.update(id, product))
          .thenThrow(new RuntimeException("Update failed"));

      RuntimeException ex =
          assertThrows(RuntimeException.class, () -> controller.updateProduct(id, request));
      assertEquals("Update failed", ex.getMessage());
    }
  }


  @Test
  void updateProduct_shouldReturnOkWithNullBody_whenMapperToResponseReturnsNull() {
    String id = "5";
    UpdateProductRequest request = mock(UpdateProductRequest.class);
    Product product = mock(Product.class);
    Product updatedProduct = mock(Product.class);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class)) {
      productMapperMock.when(() -> ProductMapper.toDomain(request)).thenReturn(product);
      when(updateProductUseCase.update(id, product)).thenReturn(updatedProduct);
      productMapperMock.when(() -> ProductMapper.toResponse(updatedProduct)).thenReturn(null);

      ResponseEntity<ProductResponse> result = controller.updateProduct(id, request);

      assertEquals("200 OK", result.getStatusCode().toString());
      assertNull(result.getBody());
    }
  }

}
