package com.desafiofinal.praticafinal.dto.requestResponseDto;

import com.desafiofinal.praticafinal.dto.ProductDTO;
import com.desafiofinal.praticafinal.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Long id;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
    }

    public static Product convertToProductResponseDTO(ProductResponseDTO productResponseDTO) {
        return Product.builder()
                .id(productResponseDTO.getId())
                .build();
    }
}
