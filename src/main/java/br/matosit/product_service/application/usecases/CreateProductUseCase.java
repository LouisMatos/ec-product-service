package br.matosit.product_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductAlreadyExistsException;

@Service
public class CreateProductUseCase {

  private final ProductRepository productRepository;

  Logger log = LoggerFactory.getLogger(CreateProductUseCase.class);

  public CreateProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(Product product) {
  

    log.info("Validando se o produto já existe: {}", product.getName());

	Product existingProduct = productRepository.findByName(product.getName());
	log.info("Produto existente: {}", existingProduct);

	if (existingProduct != null) {
	  log.warn("Produto já existe: {}", product.getName());
	  throw new ProductAlreadyExistsException(product.getName());
	}

	log.info("Criando produto: {}", product.getName());
	return productRepository.save(product);
  }
}
