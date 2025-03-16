package br.matosit.product_service.presentation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.product_service.application.usecases.UpdateProductUseCase;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.presentation.mappers.ProductMapper;
import br.matosit.product_service.presentation.requests.UpdateProductRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/products")
public class UpdateProductController {

  private final UpdateProductUseCase updateProductUseCase;

  Logger log = LoggerFactory.getLogger(UpdateProductController.class);

  public UpdateProductController(UpdateProductUseCase updateProductUseCase) {
    this.updateProductUseCase = updateProductUseCase;
  }


  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable String id,
      @Valid @RequestBody UpdateProductRequest request) {
    
    Product product = ProductMapper.toDomain(request);
    Product updatedProduct = updateProductUseCase.execute(id, product);
    return ResponseEntity.ok(updatedProduct);
  }



}
