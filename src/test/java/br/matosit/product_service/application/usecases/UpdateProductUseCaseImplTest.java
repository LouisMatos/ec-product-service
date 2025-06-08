package br.matosit.product_service.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import br.matosit.product_service.application.ports.out.ProductRepository;
import br.matosit.product_service.domain.entities.Product;
import br.matosit.product_service.domain.exceptions.ProductNotFoundException;

class UpdateProductUseCaseImplTest {

  private ProductRepository productRepository;
  private UpdateProductUseCaseImpl useCase;

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepository.class);
    useCase = new UpdateProductUseCaseImpl(productRepository);
  }

  @Test
  void shouldUpdateProductSuccessfully() {
    String id = "1";
    Product existing = new Product(id, "Old", "OldDesc", 10.0, 5, "old-img");
    Product update = new Product(id, "New", "NewDesc", 20.0, 10, "new-img");

    when(productRepository.findById(id)).thenReturn(Optional.of(existing));
    when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

    Product result = useCase.update(id, update);

    assertEquals("New", result.getName());
    assertEquals("NewDesc", result.getDescription());
    assertEquals(20.0, result.getPrice());
    assertEquals(10, result.getStockQuantity());
    assertEquals("new-img", result.getImage3D());
    verify(productRepository).save(existing);
  }

  @Test
  void shouldThrowExceptionWhenProductNotFound() {
    String id = "not-found";
    Product update = new Product(id, "Name", "Desc", 10.0, 5, "img");
    when(productRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> useCase.update(id, update));
    verify(productRepository, never()).save(any());
  }

  @Test
  void shouldThrowDuplicateKeyExceptionWhenSaveFails() {
    String id = "1";
    Product existing = new Product(id, "Old", "OldDesc", 10.0, 5, "old-img");
    Product update = new Product(id, "New", "NewDesc", 20.0, 10, "new-img");

    when(productRepository.findById(id)).thenReturn(Optional.of(existing));
    when(productRepository.save(any(Product.class)))
        .thenThrow(new DuplicateKeyException("Duplicate"));

    assertThrows(DuplicateKeyException.class, () -> useCase.update(id, update));
    verify(productRepository).save(existing);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenUnexpectedErrorOnSave() {
    String id = "1";
    Product existing = new Product(id, "Old", "OldDesc", 10.0, 5, "old-img");
    Product update = new Product(id, "New", "NewDesc", 20.0, 10, "new-img");

    when(productRepository.findById(id)).thenReturn(Optional.of(existing));
    when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("DB error"));

    RuntimeException ex = assertThrows(RuntimeException.class, () -> useCase.update(id, update));
    assertTrue(ex.getMessage().contains("Erro ao atualizar o produto"));
    verify(productRepository).save(existing);
  }

  @Test
  void shouldUpdateAllFieldsCorrectly() {
    String id = "2";
    Product existing = new Product(id, "A", "B", 1.0, 1, "imgA");
    Product update = new Product(id, "B", "C", 2.0, 2, "imgB");

    when(productRepository.findById(id)).thenReturn(Optional.of(existing));
    when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

    Product result = useCase.update(id, update);

    assertEquals("B", result.getName());
    assertEquals("C", result.getDescription());
    assertEquals(2.0, result.getPrice());
    assertEquals(2, result.getStockQuantity());
    assertEquals("imgB", result.getImage3D());
  }
}
