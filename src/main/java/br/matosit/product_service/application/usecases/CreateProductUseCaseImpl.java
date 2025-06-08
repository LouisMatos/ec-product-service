package br.matosit.product_service.application.usecases;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import br.matosit.product_service.application.dto.ProductCreatedEvent;
import br.matosit.product_service.application.ports.in.CreateProductUseCase;
import br.matosit.product_service.application.ports.out.EventPublisher;
import br.matosit.product_service.application.ports.out.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductAlreadyExistsException;

@Service
public class CreateProductUseCaseImpl implements CreateProductUseCase {

  private final ProductRepository productRepository;
  private final EventPublisher eventPublisher;
  private static final Logger log = LoggerFactory.getLogger(CreateProductUseCase.class);

  public CreateProductUseCaseImpl(ProductRepository productRepository,
      EventPublisher eventPublisher) {
    this.productRepository = productRepository;
    this.eventPublisher = eventPublisher;
  }

  @Override
  public Product create(Product product) {
    validateProductDoesNotExist(product);
    Product savedProduct = productRepository.save(product);
    publishProductCreatedEventAsync(savedProduct);
    return savedProduct;
  }

  @Async("asyncExecutor")
  public void publishProductCreatedEventAsync(Product product) {
    try {
      ProductCreatedEvent event = new ProductCreatedEvent(product.getId(), product.getName(),
          product.getPrice(), product.getStockQuantity());
      eventPublisher.publish(event);
      log.info("Evento publicado com sucesso: {}", event);
    } catch (Exception e) {
      log.error("Falha ao publicar evento no Kafka. ID do Produto: {}, Erro: {}", product.getId(),
          e.getMessage(), e);
    }
  }

  private void validateProductDoesNotExist(Product product) {
    log.info("Verificando se o produto já existe: {}", product.getName());
    Optional<Product> existingProduct =
        Optional.ofNullable(productRepository.findByName(product.getName()));
    if (existingProduct.isPresent()) {
      log.warn("Produto já existe: {}", product.getName());
      throw new ProductAlreadyExistsException(product.getName());
    }
  }

}
