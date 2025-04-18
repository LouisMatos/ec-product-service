package br.matosit.product_service.presentation.mappers;

import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.presentation.requests.CreateProductRequest;
import br.matosit.product_service.presentation.requests.UpdateProductRequest;
import jakarta.validation.Valid;

public class ProductMapper {

  public static Product toDomain(@Valid CreateProductRequest request) {
    return new Product(request.name(), request.description(), request.price(),
        request.stockQuantity(), request.image3D());
  }

  public static Product toDomain(@Valid UpdateProductRequest request) {
    return new Product(request.name(), request.description(), request.price(),
        request.stockQuantity(), request.image3D());
  }

}
