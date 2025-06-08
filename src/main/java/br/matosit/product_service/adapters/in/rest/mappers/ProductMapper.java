package br.matosit.product_service.adapters.in.rest.mappers;

import br.matosit.product_service.adapters.in.rest.requests.CreateProductRequest;
import br.matosit.product_service.adapters.in.rest.requests.UpdateProductRequest;
import br.matosit.product_service.adapters.in.rest.responses.ProductResponse;
import br.matosit.product_service.domain.entities.Product;
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

  public static ProductResponse toResponse(Product product) {
    return new ProductResponse(product.getId(), product.getName(), product.getDescription(),
        product.getPrice(), product.getStockQuantity(), product.getImage3D());
  }

  public static Product toProduct(UpdateProductRequest updateProductRequest) {
    Product product = new Product();
    product.setName(updateProductRequest.name());
    product.setDescription(updateProductRequest.description());
    product.setPrice(updateProductRequest.price());
    product.setStockQuantity(updateProductRequest.stockQuantity());
    product.setImage3D(updateProductRequest.image3D());
    return product;
  }

}
