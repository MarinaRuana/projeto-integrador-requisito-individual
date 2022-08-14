package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.model.Buyer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDto {

    private long buyerId;

    private String buyerName;

    public BuyerDto (Buyer buyer){
        this.buyerId= buyer.getBuyerId();
    }

    public static Buyer convertDtoToBuyer (BuyerDto buyerDto){
        return Buyer.builder()
                .buyerId(buyerDto.getBuyerId())
                .build();
    }

}
