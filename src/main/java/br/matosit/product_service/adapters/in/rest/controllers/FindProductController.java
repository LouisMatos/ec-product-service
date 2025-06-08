package br.matosit.product_service.adapters.in.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.product_service.adapters.in.rest.mappers.ProductMapper;
import br.matosit.product_service.adapters.in.rest.responses.ProductResponse;
import br.matosit.product_service.application.ports.in.FindProductUseCase;
import br.matosit.product_service.domain.entities.Product;

@RestController
@RequestMapping("/api/products")
public class FindProductController {

  private final FindProductUseCase findProductUseCase;

  private static final Logger log = LoggerFactory.getLogger(FindProductController.class);

  public FindProductController(FindProductUseCase findProductUseCase) {
    this.findProductUseCase = findProductUseCase;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
    log.info("Buscando produto com id: {}", id);
    Product product = findProductUseCase.find(id);
    log.info("Produto encontrado: {}", product);
    return ResponseEntity.ok(ProductMapper.toResponse(product));
  }
}
