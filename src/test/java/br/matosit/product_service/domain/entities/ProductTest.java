package br.matosit.product_service.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ProductTest {

  @Test
  void updatePriceWithValidValue() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    product.updatePrice(150.0);
    assertEquals(150.0, product.getPrice());
  }

  @Test
  void updatePriceWithNullValueThrowsException() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    assertThrows(IllegalArgumentException.class, () -> product.updatePrice(null));
  }

  @Test
  void updatePriceWithNegativeValueThrowsException() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    assertThrows(IllegalArgumentException.class, () -> product.updatePrice(-50.0));
  }

  @Test
  void updateStockQuantityWithValidValue() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    product.updateStockQuantity(20);
    assertEquals(20, product.getStockQuantity());
  }

  @Test
  void updateStockQuantityWithNullValueThrowsException() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    assertThrows(IllegalArgumentException.class, () -> product.updateStockQuantity(null));
  }

  @Test
  void updateStockQuantityWithNegativeValueThrowsException() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    assertThrows(IllegalArgumentException.class, () -> product.updateStockQuantity(-5));
  }

  @Test
  void updateImage3DWithValidValue() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    product.updateImage3D("newImage3D");
    assertEquals("newImage3D", product.getImage3D());
  }

  @Test
  void updateImage3DWithNullValueThrowsException() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    assertThrows(IllegalArgumentException.class, () -> product.updateImage3D(null));
  }

  @Test
  void updateImage3DWithEmptyValueThrowsException() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    assertThrows(IllegalArgumentException.class, () -> product.updateImage3D(""));
  }

  @Test
  void updateImage3DWithWhitespaceValueThrowsException() {
    Product product = new Product("Product1", "Description1", 100.0, 10, "image3D1");
    assertThrows(IllegalArgumentException.class, () -> product.updateImage3D("   "));
  }
}
