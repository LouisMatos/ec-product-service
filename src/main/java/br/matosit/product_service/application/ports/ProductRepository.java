package br.matosit.product_service.application.ports;

import java.util.Optional;

import br.matosit.product_service.domain.entities.Product;

public interface ProductRepository {

  Product save(Product customer);

  Optional<Product> findById(String id);

  void deleteById(String id);

  Product findByName(String name);

  boolean existsById(String id);

}
