package com.desafiofinal.praticafinal.dto;


import com.desafiofinal.praticafinal.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private long id;

    private BuyerDto payer;

    private double value;

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
