package br.matosit.product_service.presentation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.matosit.product_service.application.usecases.CreateProductUseCase;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.presentation.mappers.ProductMapper;
import br.matosit.product_service.presentation.requests.CreateProductRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/products")
public class CreateProductController {

  private final CreateProductUseCase createProductUseCase;

  Logger log = LoggerFactory.getLogger(CreateProductController.class);

  public CreateProductController(CreateProductUseCase createProductUseCase) {
    this.createProductUseCase = createProductUseCase;
  }

  @PostMapping
  public ResponseEntity<Product>productCustomer(
      @Valid @RequestBody CreateProductRequest request) {
    
    Product product = ProductMapper.toDomain(request);
    Product createdCustomer = createProductUseCase.execute(product);
    log.info("Cliente criado com sucesso: {}", createdCustomer.getId());
    return ResponseEntity.ok(createdCustomer);
  }

}
