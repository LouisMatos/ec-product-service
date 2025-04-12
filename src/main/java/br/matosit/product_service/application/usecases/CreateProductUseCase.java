package br.matosit.product_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductAlreadyExistsException;

import java.util.Optional;

@Service
public class CreateProductUseCase {

  private final ProductRepository productRepository;
  private static final Logger log = LoggerFactory.getLogger(CreateProductUseCase.class);

  public CreateProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(Product product) {
    validateProductDoesNotExist(product);
    log.info("Creating product: {}", product.getName());
    return productRepository.save(product);
  }

  private void validateProductDoesNotExist(Product product) {
    log.info("Validating if the product already exists: {}", product.getName());
    Optional<Product> existingProduct =
        Optional.ofNullable(productRepository.findByName(product.getName()));
    if (existingProduct.isPresent()) {
      log.warn("Product already exists: {}", product.getName());
      throw new ProductAlreadyExistsException(product.getName());
    }
  }
}
