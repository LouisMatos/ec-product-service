package br.matosit.product_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.product_service.application.ports.in.FindProductUseCase;
import br.matosit.product_service.application.ports.out.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

@Service
public class FindProductUseCaseImpl implements FindProductUseCase {

  private final ProductRepository productRepository;
  private static final Logger log = LoggerFactory.getLogger(FindProductUseCaseImpl.class);

  public FindProductUseCaseImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product find(String id) {
    log.info("Buscando produto com id: {}", id);

    Product product = productRepository.findById(id).orElseThrow(() -> {
      log.warn("Produto com id {} não encontrado", id);
      return new ProductNotFoundException(id);
    });

    return product;
  }
}
