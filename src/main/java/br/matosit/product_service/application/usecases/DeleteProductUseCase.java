package br.matosit.product_service.application.usecases;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

@Service
public class DeleteProductUseCase {

  private final ProductRepository productRepository;

  Logger log = LoggerFactory.getLogger(DeleteProductUseCase.class);

  public DeleteProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void execute(String id) {
    // Validação de negócio
    log.info("Deletando produto com id: {}", id);

    Optional<Product> existingProduct = productRepository.findById(id);
    log.info("Produto encontrado: {}", existingProduct);

    if (existingProduct.isEmpty()) {
      log.warn("Produto não encontrado");
      throw new ProductNotFoundException(id);
    }

    // Persistir e retornar
    log.info("Deletando produto");
    productRepository.deleteById(id);
  }
}
