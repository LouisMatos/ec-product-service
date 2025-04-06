package br.matosit.product_service.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

  @Id
  private String id;

  private String name;

  private String description;

  private Double price;

  private Integer stockQuantity;

  private String image3D;

  public Product(String name, String description, Double price, Integer stockQuantity,
      String image3D) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.image3D = image3D;
  }

  public Product(String id, String name, String description, Double price, Integer stockQuantity,
      String image3D) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.image3D = image3D;
  }

  // Getters
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Double getPrice() {
    return price;
  }

  public Integer getStockQuantity() {
    return stockQuantity;
  }

  public String getImage3D() {
    return image3D;
  }

  // Setters
  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public void setStockQuantity(Integer stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public void setImage3D(String image3D) {
    this.image3D = image3D;
  }
  
  // Métodos de domínio
  public void updatePrice(Double newPrice) {
    if (newPrice == null || newPrice < 0) {
      throw new IllegalArgumentException("Preço não pode ser nulo ou negativo");
    }
    this.price = newPrice;
  }

  public void updateStockQuantity(Integer newStockQuantity) {
    if (newStockQuantity == null || newStockQuantity < 0) {
      throw new IllegalArgumentException("Quantidade em estoque não pode ser nula ou negativa");
    }
    this.stockQuantity = newStockQuantity;
  }

  public void updateImage3D(String newImage3D) {
    if (newImage3D == null || newImage3D.trim().isEmpty()) {
      throw new IllegalArgumentException("Imagem 3D não pode ser vazia");
    }
    this.image3D = newImage3D;
  }

  @Override
  public String toString() {
    return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price="
        + price + ", stockQuantity=" + stockQuantity + ", image3D=" + image3D + "]";
  }

}
