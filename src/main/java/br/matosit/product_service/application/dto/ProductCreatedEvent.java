package br.matosit.product_service.application.dto;

public class ProductCreatedEvent {
  private final String id;
  private final String name;
  private final double price;
  private final int stockQuantity;

  public ProductCreatedEvent(String id, String name, double price, int stockQuantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }


}
