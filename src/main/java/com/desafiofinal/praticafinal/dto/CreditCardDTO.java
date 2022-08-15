package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.model.CreditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDTO {

    private long id;

    @NotNull(message = "Please enter a valid card owner")
    private BuyerDto cardOwner;

    @NotBlank(message = "Pleas enter a valid card number")
    @Pattern(regexp = "[0-9]*", message = "Only digits are allowed")
    @Size(max = 6, min = 6, message = "Card number has 6 digits")
    private String cardNumber;

    @NotNull(message = "Total limit cannot be null")
    @DecimalMin(value = "100", message = "Total limit cannot be less than 100")
    private Double limitTotal;

    @NotNull
    @DecimalMin(value = "1", message = "Limit available cannot be less than 1")
    private Double limitAvailable;
    @NotNull(message = "Total limit cannot be null")
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
