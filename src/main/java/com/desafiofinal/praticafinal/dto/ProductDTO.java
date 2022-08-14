package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String productType;

    private LocalDate validateDate;

    private double price;

    private String productId;

    private String productName;

    private SellerDTO seller;

    private Double bulk;

    @JsonIgnore
//    private List<BatchStock> batchList;

    public ProductDTO(Product product){
        this.id = product.getId();
        this.productType = product.getProductType();
        this.validateDate = product.getValidateDate();
        this.price = product.getPrice();
        this.productName = product.getProductName();
        this.seller = new SellerDTO(product.getSeller());
        this.bulk = product.getBulk();
//        this.batchList = product.getBatchList();

    }

    public static Product convertDtoToProduct (ProductDTO productDto){
        return Product.builder()
                .id(productDto.getId())
                .productType(productDto.getProductType())
                .validateDate(productDto.getValidateDate())
                .price(productDto.getPrice())
                .productName(productDto.getProductName())
                .seller(SellerDTO.convertToSeller(productDto.getSeller()))
                .build();
    }


    public static List<ProductDTO> convertToDTO(List<Product> productDTOList){
        return productDTOList.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

}
