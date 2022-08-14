package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.model.*;
import com.desafiofinal.praticafinal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InBoundOrderImpService implements IinBoundOrderService {

    @Autowired
    private InBoundOrderRepo inBoundOrderRepo;

    @Autowired
    private IBatchStockRepo batchStockRepo;

    @Autowired
    private ISectorRepo sectorRepo;

    @Autowired
    private IProductRepo productRepo;


    @Transactional
    @Override
    public InBoundOrder saveInBoundOrder (InBoundOrder newOrder) {
        InBoundOrder newInBoundOrder = verifyInBoundOrder(newOrder);
        if(newInBoundOrder.getOrderId() != 0) {
            throw new ElementAlreadyExistsException("Order already exists");
        }
        List<BatchStock> batchStockList = verifyBatch(newInBoundOrder);
        verifySector(newInBoundOrder);
        newInBoundOrder.setBatchStockList(batchStockList);
        batchStockRepo.saveAll(batchStockList);
        return inBoundOrderRepo.save(newInBoundOrder);
    }

    @Override
    public InBoundOrder updateInBoundOrder (InBoundOrder inBoundOrder)  {
        InBoundOrder updatedOrder = verifyInBoundOrder(inBoundOrder);
        if(updatedOrder.getOrderId() == 0) {
            throw new ElementNotFoundException("Inbound does not exists");
        }
        verifyBatchStockPertenceToInBoundOrder(inBoundOrder.getBatchStockList(), inBoundOrder);
        return inBoundOrderRepo.save(updatedOrder);
    }

    private InBoundOrder verifyInBoundOrder(InBoundOrder inboundOrder) {
        Optional<InBoundOrder> foundInboundOrder = inBoundOrderRepo.findById(inboundOrder.getOrderId());
        if(foundInboundOrder.isPresent()){
            inboundOrder.setOrderId(foundInboundOrder.get().getOrderId());
        } else {
            inboundOrder.setOrderId(0L);
        }
        return inboundOrder;
    }

    private List<BatchStock> verifyBatch(InBoundOrder inBoundOrder) {
        return inBoundOrder.getBatchStockList().stream().map(bs -> {
            Product product = verifyProduct(bs);

            return  new BatchStock(
                    bs.getBatchId(),
                    bs.getCurrentTemperature(),
                    bs.getMinimumTemperature(),
                    bs.getInitialQuantity(),
                    bs.getCurrentQuantity(),
                    bs.getManufacturingDate(),
                    bs.getManufacturingTime(),
                    bs.getDueDate(),
                    inBoundOrder,
                    product);
        }).collect(Collectors.toList());
    }

    private void verifySector(InBoundOrder inboundOrder) {
        Optional<Sector> foundSector = Optional.ofNullable(sectorRepo.findById(inboundOrder.getSector().getSectorId())
                .orElseThrow(() -> new ElementNotFoundException("Sector does not exist")));
        inboundOrder.setSector(foundSector.get());
    }

    private Product verifyProduct(BatchStock batchStock) {
        Optional<Product> foundProduct = Optional.ofNullable(productRepo.findById(batchStock.getProduct().getId())
                .orElseThrow(() -> new ElementNotFoundException("Product does not exist")));
                    return foundProduct.get();

    }

    private void verifyBatchStockPertenceToInBoundOrder(List<BatchStock> batchStockList, InBoundOrder inBoundOrder) {
        for(BatchStock responseStock : batchStockList) {
            Optional<BatchStock> foundBatch = batchStockRepo.findById(responseStock.getBatchId());
            if(foundBatch.isPresent()) {
                Product foundProduct = verifyProduct(foundBatch.get());
                foundBatch.get().setProduct(foundProduct);
                if ((foundBatch.get().getInBoundOrder().getOrderId() == inBoundOrder.getOrderId())) {
                    responseStock.setInBoundOrder(inBoundOrder);
                    batchStockRepo.save(responseStock);
                } else {
                    throw new ElementNotFoundException("Batch stock does not belong to this inbound order");
                }
            } else {
                throw new ElementNotFoundException("Batch stock does not exist");
            }
        }
    }
}


