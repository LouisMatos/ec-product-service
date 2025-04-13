package br.matosit.product_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

@Service
public class DeleteProductUseCase {

  private final ProductRepository productRepository;
  private static final Logger log = LoggerFactory.getLogger(DeleteProductUseCase.class);

  public DeleteProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void execute(String id) {
    log.info("Tentando deletar o produto com id: {}", id);

    // Validação e exclusão em uma única operação
    boolean deleted = productRepository.existsById(id) && deleteProduct(id);

    if (!deleted) {
      log.warn("Produto com id {} não encontrado", id);
      throw new ProductNotFoundException(id);
    }

    log.info("Produto com id {} deletado com sucesso", id);
  }

  private boolean deleteProduct(String id) {
    try {
      productRepository.deleteById(id);
      return true;
    } catch (Exception e) {
      log.error("Erro ao deletar o produto com id {}: {}", id, e.getMessage());
      return false;
    }
  }
}
