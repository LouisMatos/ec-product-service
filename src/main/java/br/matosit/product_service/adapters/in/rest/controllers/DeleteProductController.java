package br.matosit.product_service.adapters.in.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.product_service.application.ports.in.DeleteProductUseCase;

@RestController
@RequestMapping("/api/products")
public class DeleteProductController {

  private final DeleteProductUseCase deleteProductUseCase;

  private static final Logger log = LoggerFactory.getLogger(DeleteProductController.class);

  public DeleteProductController(DeleteProductUseCase deleteProductUseCase) {
    this.deleteProductUseCase = deleteProductUseCase;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
    log.info("Deletando produto com id: {}", id);
    deleteProductUseCase.delete(id);
    log.info("Produto deletado com sucesso");
    return ResponseEntity.noContent().build();
  }
}
