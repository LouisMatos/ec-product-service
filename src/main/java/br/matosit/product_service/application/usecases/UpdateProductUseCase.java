package br.matosit.product_service.application.usecases;

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
    private static final Logger log = LoggerFactory.getLogger(UpdateProductUseCase.class);

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product execute(String id, Product product) {
        log.info("Iniciando atualização do produto com ID: {}", id);

        Product productToUpdate = findExistingProduct(id);
        updateProductFields(productToUpdate, product);

        log.info("Persistindo produto atualizado...");
        return saveUpdatedProduct(productToUpdate);
    }

    private Product findExistingProduct(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produto com ID {} não encontrado", id);
                    return new ProductNotFoundException(id);
                });
    }

    private void updateProductFields(Product productToUpdate, Product newProductData) {
        log.info("Atualizando campos do produto...");
        productToUpdate.setName(newProductData.getName());
        productToUpdate.setDescription(newProductData.getDescription());
        productToUpdate.setPrice(newProductData.getPrice());
        productToUpdate.setStockQuantity(newProductData.getStockQuantity());
        productToUpdate.setImage3D(newProductData.getImage3D());
    }

    private Product saveUpdatedProduct(Product product) {
        try {
            Product updatedProduct = productRepository.save(product);
            log.info("Produto atualizado com sucesso: {}", updatedProduct);
            return updatedProduct;
        } catch (DuplicateKeyException e) {
            log.error("Erro de chave duplicada ao atualizar o produto", e);
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar o produto", e);
            throw new RuntimeException("Erro ao atualizar o produto", e);
        }
    }
}
