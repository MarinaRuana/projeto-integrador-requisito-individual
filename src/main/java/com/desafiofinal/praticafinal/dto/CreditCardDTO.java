package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.model.CreditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDTO {

    private long id;

    private BuyerDto cardOwner;

    private Long cardNumber;

    private Double limitTotal;

    private Double limitAvailable;

    private Boolean status;


    public CreditCardDTO(CreditCard creditCard){
        this.id = creditCard.getId();
        this.cardOwner = new BuyerDto(creditCard.getIdBuyer());
        this.cardNumber = creditCard.getCardNumber();
        this.limitTotal = creditCard.getLimitTotal();
        this.limitAvailable = creditCard.getLimitAvailable();
        this.status = creditCard.isStatus();

    }

    public static CreditCard convertToCreditCard(CreditCardDTO creditCardDTO){
        return CreditCard.builder()
                .id(creditCardDTO.getId())
                .idBuyer(BuyerDto.convertDtoToBuyer(creditCardDTO.getCardOwner()))
                .cardNumber(creditCardDTO.getCardNumber())
                .limitTotal(creditCardDTO.getLimitTotal())
                .limitAvailable(creditCardDTO.getLimitAvailable())
                .status(creditCardDTO.getStatus())
                .build();
    }

    public static List<CreditCardDTO> covertListToDTO(List<CreditCard> creditCardList){
        return creditCardList.stream()
                .map(CreditCardDTO::new)
                .collect(Collectors.toList());
    }
}
