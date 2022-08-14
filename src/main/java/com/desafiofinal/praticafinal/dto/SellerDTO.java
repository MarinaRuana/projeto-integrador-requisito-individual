package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {

    private long idSeller;
    private String sellerName;

    public SellerDTO(Seller seller) {
        this.idSeller = seller.getId();
        this.sellerName = seller.getSellerName();
    }
    public static Seller convertToSeller(SellerDTO sellerDTO) {
        return Seller.builder()
                .id(sellerDTO.getIdSeller())
                .sellerName(sellerDTO.getSellerName())
                .build();
    }
}
