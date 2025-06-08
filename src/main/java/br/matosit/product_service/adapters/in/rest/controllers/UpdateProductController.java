package br.matosit.product_service.adapters.in.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.product_service.adapters.in.rest.mappers.ProductMapper;
import br.matosit.product_service.adapters.in.rest.requests.UpdateProductRequest;
import br.matosit.product_service.adapters.in.rest.responses.ProductResponse;
import br.matosit.product_service.application.ports.in.UpdateProductUseCase;
import br.matosit.product_service.domain.entities.Product;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class UpdateProductController {

  private final UpdateProductUseCase updateProductUseCase;

  private final Logger log = LoggerFactory.getLogger(UpdateProductController.class);

  public UpdateProductController(UpdateProductUseCase updateProductUseCase) {
    this.updateProductUseCase = updateProductUseCase;
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id,
      @Valid @RequestBody UpdateProductRequest request) {

    log.info("Atualizando produto {} com id: {}", request, id);

    Product updatedProduct = updateProductUseCase.update(id, ProductMapper.toDomain(request));

    log.info("Produto atualizado com sucesso");
    return ResponseEntity.ok(ProductMapper.toResponse(updatedProduct));
  }

}
