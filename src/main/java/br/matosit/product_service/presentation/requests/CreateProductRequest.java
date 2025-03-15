package br.matosit.product_service.presentation.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(
    @NotBlank(message = "Nome é obrigatório") String name,
    @NotBlank(message = "Descrição é obrigatória") String description,
    @NotNull(message = "Preço é obrigatório") Double price,
    @NotNull(message = "Quantidade em estoque é obrigatória") Integer stockQuantity,
    @NotBlank(message = "Imagem 3D é obrigatória") String image3D
) {
}
