package br.matosit.product_service.presentation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.matosit.product_service.application.usecases.FindProductUseCase;
import br.matosit.product_service.domain.entities.Product;

@RestController
@RequestMapping("/api/products")
public class FindProductController {

  private final FindProductUseCase findProductUseCase;

  Logger log = LoggerFactory.getLogger(FindProductController.class);

  public FindProductController(FindProductUseCase findProductUseCase) {
    this.findProductUseCase = findProductUseCase;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable String id) {
    log.info("Buscando produto com id {}", id);
    Product product = findProductUseCase.execute(id);
    log.info("Produto encontrado: {}", product);
    return ResponseEntity.ok(product);
  }
}
