package com.desafiofinal.praticafinal.dto;


import com.desafiofinal.praticafinal.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private long id;

    @NotNull(message = "Please enter a valid payer")
    private BuyerDto payer;

    @NotNull(message = "Payment value cannot be null")
    @DecimalMin(value = "1", message = "Payment value cannot be less than 1")
    private Double value;

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.payer = new BuyerDto(payment.getPayer());
        this.value = payment.getValue();
    }

    public static Payment convertToPayment(PaymentDTO paymentDTO){
        return Payment.builder()
                .id(paymentDTO.getId())
                .payer(BuyerDto.convertDtoToBuyer(paymentDTO.getPayer()))
                .value(paymentDTO.getValue())
                .build();
    }

}
