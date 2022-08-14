package com.desafiofinal.praticafinal.dto;

import com.desafiofinal.praticafinal.model.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {

    private Long purchaseId;

    private CartDto cart;

    @NotNull(message = "Please enter a valid batch stock")
    private BatchStockDTO batchStock;

    @NotNull(message = "Price per Product cannot be null")
    private Double pricePerProduct;

    @NotNull(message = "Product quantity cannot be null")
    private Integer productQuantity;

    public PurchaseDTO(Purchase purchase){
        this.purchaseId = purchase.getCartBatchStockId();
        this.pricePerProduct= purchase.getPricePerProduct();
        this.productQuantity= purchase.getProductQuantity();
    }

    public static Purchase convertDtoToPurchase(PurchaseDTO purchaseDTO){
        return Purchase.builder()
                .pricePerProduct(purchaseDTO.pricePerProduct)
                .productQuantity(purchaseDTO.getProductQuantity())
                .batchStock(BatchStockDTO.convertBatchStockDtoToBatchStockIdOnly(purchaseDTO.getBatchStock()))
                .build();
    }

    public static List<Purchase> convertToListEntity(List<PurchaseDTO> purchaseDTOList){
        return purchaseDTOList.stream()
                .map(PurchaseDTO::convertDtoToPurchase)
                .collect(Collectors.toList());
    }
}
