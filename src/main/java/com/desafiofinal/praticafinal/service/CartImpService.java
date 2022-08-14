package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ExceededCapacityException;
import com.desafiofinal.praticafinal.model.*;
import com.desafiofinal.praticafinal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartImpService implements ICartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private IBatchStockRepo batchStockRepo;

    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    private BuyerRepo buyerRepo;

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private ISectorRepo sectorRepo;

    @Transactional
    @Override
    public Double createPurchase(Cart cart){

        Buyer foundBuyer = verifyBuyer(cart.getBuyer());
        cart.setBuyer(foundBuyer);

        List<Purchase> foundPurchaseList = verifyCartBatchStock(cart.getListPurchase(), cart);
        cart.setListPurchase(foundPurchaseList);

        double totalPrice = getTotalPrice(cart);
        cart.setOrderStatus("Open");
        cart.setTotalPrice(totalPrice);
        cartRepo.save(cart);
        purchaseRepo.saveAll(cart.getListPurchase());

        return totalPrice;
    }

    @Override
    public List<BatchStock> getProducts(long purchaseId){ //purchase = cartId
        Optional <Cart> foundCart = cartRepo.findById(purchaseId);
        List<BatchStock> batchStockList = new ArrayList<>();

        for (Purchase purchase : foundCart.get().getListPurchase()){
            BatchStock foundBatchStock = purchase.getBatchStock();
            batchStockList.add(foundBatchStock);
        }
        return batchStockList;
    }

    @Override
    public String updateStatus(long purchaseId){
        Optional<Cart> foundCart = verifyIfCartExists(purchaseId);
        foundCart.get().setOrderStatus("Finished");
        cartRepo.save(foundCart.get());
        return "Order completed successfully";
    }

    private double getTotalPrice(Cart cart) {
        double totalPrice = 0d;
        for (Purchase purchase : cart.getListPurchase()) {
                double price = purchase.getPricePerProduct() * purchase.getProductQuantity();
                totalPrice += price;
        }
        return totalPrice;
    }

    private Buyer verifyBuyer(Buyer buyer) {
        Optional<Buyer> foundBuyer =  buyerRepo.findById(buyer.getBuyerId());
        if(foundBuyer.isPresent()) {
            return foundBuyer.get();
        }else{
            throw new ElementAlreadyExistsException("Buyer does not exists");
        }
    }

    private List<Purchase> verifyCartBatchStock(List<Purchase> purchaseList, Cart cart)  {
        List<Purchase> purchaseListVerified = new ArrayList<>();
        for(Purchase cbs : purchaseList) {
            BatchStock foundBatchStock = verifyBatchStock(cbs.getBatchStock());
            cbs.setIdCart(cart);
            cbs.setBatchStock(foundBatchStock);
            purchaseListVerified.add(cbs);
        }
       return purchaseListVerified;
    }


    private BatchStock verifyBatchStock(BatchStock batchStock) {
        Optional<BatchStock> foundBatchStock = batchStockRepo.findById(batchStock.getBatchId());
        if (foundBatchStock.isPresent()) {
            return foundBatchStock.get();
        } else {
            throw new ElementAlreadyExistsException("Batch stock does not exist");
        }
    }

    private Optional<Cart> verifyIfCartExists(long purchaseId) {
        Optional <Cart> foundCart = cartRepo.findById(purchaseId);
        List<BatchStock> batchStockList = new ArrayList<>();

        if(foundCart.isEmpty()){
            throw new ElementAlreadyExistsException("Cart does not exist");
        }else{
            verifyStatus(foundCart, batchStockList);
        }
        return foundCart;
    }

    private void verifyStatus(Optional<Cart> foundCart, List<BatchStock> batchStockList) {
        String cartStatus = foundCart.get().getOrderStatus();
        if(cartStatus.equalsIgnoreCase("Open")){
            verifyQuantity(foundCart, batchStockList);
        } else {
            throw new ElementAlreadyExistsException("Cart already finished");
        }
    }

    private void verifyQuantity(Optional<Cart> foundCart, List<BatchStock> batchStockList)  {
        for (Purchase purchase : foundCart.get().getListPurchase()){
            BatchStock foundBatchStock = purchase.getBatchStock();
                batchStockList.add(foundBatchStock);
                long cartQuantity= purchase.getProductQuantity();
                if(purchase.getProductQuantity()<=foundBatchStock.getCurrentQuantity()){
                    verifyCapacity(foundBatchStock, cartQuantity);
                }else{
                    throw new ExceededCapacityException("Quantity unavailable");
                }
        }
    }

    private void verifyCapacity(BatchStock foundBatchStock, long cartQuantity) {
        long stockQuantity = foundBatchStock.getCurrentQuantity();
        long newQuantity = stockQuantity - cartQuantity;
        double totalItemsVolume = foundBatchStock.getProduct().getBulk()*cartQuantity;
        double increaseCapacity = foundBatchStock.getInBoundOrder().getSector().getCapacity()+ totalItemsVolume;
        double maxCapacity = foundBatchStock.getInBoundOrder().getSector().getMaxCapacity();

        if (increaseCapacity<=maxCapacity){
            foundBatchStock.getInBoundOrder().getSector().setCapacity(increaseCapacity);
            foundBatchStock.setCurrentQuantity(newQuantity);

            batchStockRepo.save(foundBatchStock);
            sectorRepo.save(foundBatchStock.getInBoundOrder().getSector());

        } else {
            throw new ExceededCapacityException("Maximum capacity has been exceeded");
        }
    }
}
