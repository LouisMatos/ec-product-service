package br.matosit.product_service.application.ports.in;

import br.matosit.product_service.domain.entities.Product;

public interface FindProductUseCase {
  Product find(String id);
}
