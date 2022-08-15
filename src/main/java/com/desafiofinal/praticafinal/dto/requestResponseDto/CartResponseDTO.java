package com.desafiofinal.praticafinal.dto.requestResponseDto;

import com.desafiofinal.praticafinal.dto.BuyerDto;
import com.desafiofinal.praticafinal.dto.CartDto;
import com.desafiofinal.praticafinal.dto.CreditCardDTO;
import com.desafiofinal.praticafinal.dto.PurchaseDTO;
import com.desafiofinal.praticafinal.model.Buyer;
import com.desafiofinal.praticafinal.model.Cart;
import com.desafiofinal.praticafinal.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {

    private long cartId;

    @NotNull(message = "Please enter a valid buyer")
    private BuyerDto buyer;

    private Double totalPrice;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotNull(message = "Date cannot be null. Format: yyyy/MM/dd")
    private LocalDate date;

    private String orderStatus;

    private Long creditCard;

    public CartResponseDTO(Cart cart){
        this.cartId=cart.getCartId();
        this.buyer = new BuyerDto(cart.getBuyer());
        this.totalPrice=cart.getTotalPrice();
        this.date = cart.getDate();
        this.orderStatus = cart.getOrderStatus();
        this.creditCard = cart.getCreditCard().getCardNumber();
    }

    public static List<CartResponseDTO> convertListToDTO(List<Cart> creditCardList){
        return creditCardList.stream()
                .map(CartResponseDTO::new)
                .collect(Collectors.toList());
    }
}

