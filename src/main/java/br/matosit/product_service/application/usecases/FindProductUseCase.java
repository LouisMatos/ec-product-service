package br.matosit.product_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.product_service.application.dto.ProductDTO;
import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

@Service
public class FindProductUseCase {

  private final ProductRepository productRepository;
  private static final Logger log = LoggerFactory.getLogger(FindProductUseCase.class);

  public FindProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public ProductDTO execute(String id) {
    log.info("Buscando produto com id: {}", id);

    // Validação e busca do produto
    return productRepository.findById(id).map(this::toDTO).orElseThrow(() -> {
      log.warn("Produto com id {} não encontrado", id);
      return new ProductNotFoundException(id);
    });
  }

  private ProductDTO toDTO(Product product) {
    return new ProductDTO(product.getId(), product.getName(), product.getDescription(),
        product.getPrice());
  }
}
