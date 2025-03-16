package br.matosit.product_service.domain.exceptions;

public abstract class DomainException extends RuntimeException {
  /**
  * 
  */
  private static final long serialVersionUID = 3200813964559214466L;
  private final String code;

  protected DomainException(String message, String code) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
