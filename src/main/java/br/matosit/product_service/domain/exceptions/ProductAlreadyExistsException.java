package br.matosit.product_service.domain.exceptions;

public class ProductAlreadyExistsException extends DomainException {
  /**
  * 
  */
  private static final long serialVersionUID = -6248244341504055174L;

  public ProductAlreadyExistsException(String name) {
    super(String.format("Produto com nome %s já existe", name), "PRODUCT-001");
  }
}
