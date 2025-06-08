package br.matosit.product_service.adapters.in.rest.controllers;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.matosit.product_service.adapters.in.rest.mappers.ProductMapper;
import br.matosit.product_service.adapters.in.rest.requests.CreateProductRequest;
import br.matosit.product_service.adapters.in.rest.responses.ProductResponse;
import br.matosit.product_service.application.ports.in.CreateProductUseCase;
import br.matosit.product_service.domain.entities.Product;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/products")
public class CreateProductController {

  private final CreateProductUseCase createProductUseCase;

  private static final Logger log = LoggerFactory.getLogger(CreateProductController.class);

  public CreateProductController(CreateProductUseCase createProductUseCase) {
    this.createProductUseCase = createProductUseCase;
  }

  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(
      @Valid @RequestBody CreateProductRequest request) {
    Product product = ProductMapper.toDomain(request);
    Product createdProduct = createProductUseCase.create(product);
    log.info("Produto criado com sucesso: {}", createdProduct.getId());
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdProduct.getId()).toUri();
    return ResponseEntity.created(location).body(ProductMapper.toResponse(createdProduct));
  }

}
