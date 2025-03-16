package br.matosit.product_service.application.usecases;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

@Service
public class FindProductUseCase {

  private final ProductRepository productRepository;

  Logger log = LoggerFactory.getLogger(FindProductUseCase.class);

  public FindProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(String id) {
    // Validação de negócio
    log.info("Buscando produto com id {}", id);
    Optional<Product> existingProduct = productRepository.findById(id);
    log.info("Produto encontrado: {}", existingProduct);


    if (existingProduct.isEmpty()) {
      log.warn("Produto com id {} não encontrado", id);
      throw new ProductNotFoundException(id);
    }

    // Retornar
    log.info("Produto encontrado: {}", existingProduct.get());
    return existingProduct.get();
  }
}
