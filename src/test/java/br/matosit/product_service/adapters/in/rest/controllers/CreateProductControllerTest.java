package br.matosit.product_service.adapters.in.rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import br.matosit.product_service.adapters.in.rest.mappers.ProductMapper;
import br.matosit.product_service.adapters.in.rest.requests.CreateProductRequest;
import br.matosit.product_service.adapters.in.rest.responses.ProductResponse;
import br.matosit.product_service.application.ports.in.CreateProductUseCase;
import br.matosit.product_service.domain.entities.Product;

class CreateProductControllerTest {

  private CreateProductUseCase createProductUseCase;
  private CreateProductController controller;

  @BeforeEach
  void setUp() {
    createProductUseCase = mock(CreateProductUseCase.class);
    controller = new CreateProductController(createProductUseCase);
  }

  @Test
  void createProduct_shouldReturnCreatedResponse() {
    CreateProductRequest request = mock(CreateProductRequest.class);
    Product product = mock(Product.class);
    Product createdProduct = mock(Product.class);
    ProductResponse response = mock(ProductResponse.class);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class);
        MockedStatic<ServletUriComponentsBuilder> uriBuilderMock =
            Mockito.mockStatic(ServletUriComponentsBuilder.class)) {

      productMapperMock.when(() -> ProductMapper.toDomain(request)).thenReturn(product);
      when(createProductUseCase.create(product)).thenReturn(createdProduct);
      productMapperMock.when(() -> ProductMapper.toResponse(createdProduct)).thenReturn(response);

      URI uri = URI.create("http://localhost/api/products/1");
      UriComponents uriComponents = mock(UriComponents.class);

      ServletUriComponentsBuilder builder = mock(ServletUriComponentsBuilder.class);
      when(builder.path("/{id}")).thenReturn(builder);
      when(builder.buildAndExpand(anyString())).thenReturn(uriComponents);
      when(uriComponents.toUri()).thenReturn(uri);

      uriBuilderMock.when(ServletUriComponentsBuilder::fromCurrentRequest).thenReturn(builder);

      when(createdProduct.getId()).thenReturn("1");

      ResponseEntity<ProductResponse> result = controller.createProduct(request);

      assertEquals("201 CREATED", result.getStatusCode().toString());
      assertEquals(uri, result.getHeaders().getLocation());
      assertEquals(response, result.getBody());
    }
  }

  @Test
  void createProduct_shouldThrowException_whenUseCaseFails() {
    CreateProductRequest request = mock(CreateProductRequest.class);
    Product product = mock(Product.class);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class)) {
      productMapperMock.when(() -> ProductMapper.toDomain(request)).thenReturn(product);
      when(createProductUseCase.create(product)).thenThrow(new RuntimeException("DB error"));

      assertThrows(RuntimeException.class, () -> controller.createProduct(request));
    }
  }

  @Test
  void createProduct_shouldThrowException_whenMapperReturnsNull() {
    CreateProductRequest request = mock(CreateProductRequest.class);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class)) {
      productMapperMock.when(() -> ProductMapper.toDomain(request)).thenReturn(null);
      assertThrows(NullPointerException.class, () -> controller.createProduct(request));
    }
  }

  @Test
  void createProduct_shouldThrowException_whenCreatedProductIsNull() {
    CreateProductRequest request = mock(CreateProductRequest.class);
    Product product = mock(Product.class);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class)) {
      productMapperMock.when(() -> ProductMapper.toDomain(request)).thenReturn(product);
      when(createProductUseCase.create(product)).thenReturn(null);
      assertThrows(NullPointerException.class, () -> controller.createProduct(request));
    }
  }

  @Test
  void createProduct_shouldThrowException_whenIdIsNull() {
    CreateProductRequest request = mock(CreateProductRequest.class);
    Product product = mock(Product.class);
    Product createdProduct = mock(Product.class);

    try (MockedStatic<ProductMapper> productMapperMock = Mockito.mockStatic(ProductMapper.class);
        MockedStatic<ServletUriComponentsBuilder> uriBuilderMock =
            Mockito.mockStatic(ServletUriComponentsBuilder.class)) {

      productMapperMock.when(() -> ProductMapper.toDomain(request)).thenReturn(product);
      when(createProductUseCase.create(product)).thenReturn(createdProduct);

      when(createdProduct.getId()).thenReturn(null);

      ServletUriComponentsBuilder builder = mock(ServletUriComponentsBuilder.class);
      uriBuilderMock.when(ServletUriComponentsBuilder::fromCurrentRequest).thenReturn(builder);
      when(builder.path("/{id}")).thenReturn(builder);

      assertThrows(NullPointerException.class, () -> controller.createProduct(request));
    }
  }

}
