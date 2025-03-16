
package br.matosit.product_service.application.usecases;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

@Service
public class UpdateProductUseCase {

  private final ProductRepository productRepository;

  Logger log = LoggerFactory.getLogger(UpdateProductUseCase.class);

  public UpdateProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional
  public Product execute(String id, Product product) {
    try {

      Optional<Product> existingProduct = productRepository.findById(id);
      log.info("Produto encontrado: {}", existingProduct);

      if (existingProduct.isEmpty()) {
        log.warn("Produto não encontrado");
        throw new ProductNotFoundException(id);
      }


      log.info("Atualizando produto");

      Product customerToUpdate = existingProduct.get();
      customerToUpdate.setName(product.getName());
      customerToUpdate.setDescription(product.getDescription());
      customerToUpdate.setPrice(product.getPrice());
      customerToUpdate.setStockQuantity(product.getStockQuantity());
      customerToUpdate.setImage3D(product.getImage3D());

      log.info("Produto atualizado: {}", customerToUpdate);


      log.info("Persistindo produto atualizado");
      Product updatedProduct = productRepository.save(customerToUpdate);
      log.info("Produto atualizado persistido: {}", updatedProduct);

      return updatedProduct;
    } catch (DuplicateKeyException e) {
      log.error("Erro ao atualizar cliente", e);
      throw e;
    } catch (Exception e) {
      log.error("Erro ao atualizar cliente", e);
      throw new RuntimeException("Erro ao atualizar cliente", e);
    }
  }
}
