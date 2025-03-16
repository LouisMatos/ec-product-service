package br.matosit.product_service.domain.exceptions;

public class ProductNotFoundException extends DomainException {

  /**
   * 
   */
  private static final long serialVersionUID = 8078814249958268178L;

  public ProductNotFoundException(String id) {
    super(String.format("Produto com id %s não encontrado", id), "PRODUCT-002");
  }


}
