package br.matosit.product_service.presentation.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import br.matosit.product_service.domain.exceptions.DomainException;
import br.matosit.product_service.domain.exceptions.ProductAlreadyExistsException;
import br.matosit.product_service.presentation.responses.ErrorResponse;

public class GlobalExceptionHandlerTest {

  @Test
  void handleCustomerAlreadyExistsReturnsConflictStatus() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    ProductAlreadyExistsException ex =
        new ProductAlreadyExistsException("Product already exists");
    ResponseEntity<ErrorResponse> response = handler.handleCustomerAlreadyExists(ex);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("PRODUCT-001", response.getBody().getCode());
    assertEquals("Produto com nome Product already exists já existe", response.getBody().getMessage());
  }

  @Test
  void handleDomainExceptionReturnsBadRequestStatus() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    DomainException ex = new DomainException("Domain error", "DOMAIN-001") {
      private static final long serialVersionUID = 1L;
    };
    ResponseEntity<ErrorResponse> response = handler.handleDomainException(ex);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("DOMAIN-001", response.getBody().getCode());
    assertEquals("Domain error", response.getBody().getMessage());
  }

  @Test
  void handleValidationExceptionsReturnsBadRequestStatus() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    when(ex.getBindingResult()).thenReturn(mock(BindingResult.class));
    ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(ex);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("VALIDATION-001", response.getBody().getCode());
    assertEquals("Erro de validação nos campos", response.getBody().getMessage());
  }

  @Test
  void handleGenericExceptionReturnsInternalServerErrorStatus() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    Exception ex = new Exception("Internal server error");
    ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("INTERNAL-001", response.getBody().getCode());
    assertEquals("Ocorreu um erro interno no servidor", response.getBody().getMessage());
  }
}
