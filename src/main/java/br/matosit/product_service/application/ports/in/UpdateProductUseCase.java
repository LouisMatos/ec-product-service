package br.matosit.product_service.application.ports.in;

import br.matosit.product_service.domain.entities.Product;

public interface UpdateProductUseCase {
  Product update(String id, Product product);
}
