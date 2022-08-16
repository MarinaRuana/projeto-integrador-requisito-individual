package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.model.InBoundOrder;
import com.desafiofinal.praticafinal.repository.IBatchStockRepo;
import com.desafiofinal.praticafinal.repository.IProductRepo;
import com.desafiofinal.praticafinal.repository.ISectorRepo;
import com.desafiofinal.praticafinal.repository.InBoundOrderRepo;
import com.desafiofinal.praticafinal.utils.TestUtilsGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InBoundOrderImpServiceTest {

    @InjectMocks
    InBoundOrderImpService inBoundOrderImpService;

    @Mock
    InBoundOrderRepo inBoundOrderRepo;

    @Mock
    IBatchStockRepo batchStockRepo;

    @Mock
    ISectorRepo sectorRepo;

    @Mock
    IProductRepo productRepo;

    @BeforeEach
    public void setup(){}


    @Test
    void saveInBoundOrder_whenNewOrder() {
        BDDMockito.when(inBoundOrderRepo.save(ArgumentMatchers.any(InBoundOrder.class)))
                .thenReturn(TestUtilsGenerator.getOrderWithId());
        BDDMockito.when(productRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getProduct()));
        BDDMockito.when(batchStockRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBatchStockAndSectorWithCapacity()));
        BDDMockito.when(sectorRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getSector()));

        InBoundOrder newOrder = TestUtilsGenerator.getNewOrder();

        InBoundOrder savedOrder = inBoundOrderImpService.saveInBoundOrder(newOrder);

        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getBatchStockList()).isNotNull();
        Assertions.assertThat(savedOrder.getBatchStockList().size()).isEqualTo(3);
        Mockito.verify(inBoundOrderRepo, Mockito.atLeastOnce()).save(newOrder);
        Mockito.verify(batchStockRepo,Mockito.atLeastOnce()).saveAll(newOrder.getBatchStockList());
    }

    @Test
    void saveInBoundOrder_throwException_whenAlreadyExists() {
        BDDMockito.when(inBoundOrderRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getOrderWithId()));

        InBoundOrder newOrder = TestUtilsGenerator.getNewOrder();

        Assertions.assertThatThrownBy(()
                        -> inBoundOrderImpService.saveInBoundOrder(newOrder))
                .isInstanceOf(ElementAlreadyExistsException.class)
                .hasMessageContaining("Order already exists");
    }

    @Test
    void saveInBoundOrder_throwException_whenProductNotExists(){

        InBoundOrder newOrder = TestUtilsGenerator.getNewOrder();

        Assertions.assertThatThrownBy(()
        -> inBoundOrderImpService.saveInBoundOrder(newOrder))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Product does not exist");
    }

    @Test
    void saveInBoundOrder_throwException_whenSectorNotExists(){
        BDDMockito.when(productRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getProduct()));

        InBoundOrder newOrder = TestUtilsGenerator.getNewOrder();

        Assertions.assertThatThrownBy(()
        -> inBoundOrderImpService.saveInBoundOrder(newOrder))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Sector does not exist");
    }

    @Test
    void updateInBoundOrder_whenOrderExists(){
        BDDMockito.when(inBoundOrderRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getOrderWithId()));
        BDDMockito.when(inBoundOrderRepo.save(ArgumentMatchers.any(InBoundOrder.class)))
                .thenReturn(TestUtilsGenerator.getOrderWithId());
        BDDMockito.when(productRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getProduct()));
        BDDMockito.when(batchStockRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBatchStockAndSectorWithCapacity()));
        BDDMockito.when(sectorRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getSector()));

        InBoundOrder order = TestUtilsGenerator.getOrderWithId();
        InBoundOrder updatedOrder = inBoundOrderImpService.updateInBoundOrder(order);

        Assertions.assertThat(updatedOrder).isNotNull();
        Assertions.assertThat(updatedOrder.getBatchStockList()).isNotNull();
        Assertions.assertThat(updatedOrder.getBatchStockList().size()).isEqualTo(3);
        Mockito.verify(inBoundOrderRepo, Mockito.atLeastOnce()).save(order);
        Mockito.verify(batchStockRepo, Mockito.atLeastOnce()).save(order.getBatchStockList().get(0));
    }

    @Test
    void updateInBoundOrder_throwException_whenNotExists(){
        BDDMockito.when(inBoundOrderRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getNewOrder()));

        InBoundOrder order = TestUtilsGenerator.getOrderWithId();

        Assertions.assertThatThrownBy(()
                        -> inBoundOrderImpService.updateInBoundOrder(order))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Inbound does not exists");
    }

    @Test
    void updateInBoundOrder_throwException_whenBatchStockNotExists(){
        BDDMockito.when(inBoundOrderRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getOrderWithId()));
        BDDMockito.when(inBoundOrderRepo.save(ArgumentMatchers.any(InBoundOrder.class)))
                .thenReturn(TestUtilsGenerator.getOrderWithId());
        BDDMockito.when(productRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getProduct()));
        BDDMockito.when(sectorRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getSector()));

        InBoundOrder order = TestUtilsGenerator.getOrderWithId();

        Assertions.assertThatThrownBy(()
                        -> inBoundOrderImpService.updateInBoundOrder(order))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Batch stock does not exist");
    }
    @Test
    void updateInBoundOrder_throwException_whenBatchStockNotBelongsToTheOrder(){
        BDDMockito.when(inBoundOrderRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getOrderWithId()));
        BDDMockito.when(inBoundOrderRepo.save(ArgumentMatchers.any(InBoundOrder.class)))
                .thenReturn(TestUtilsGenerator.getOrderWithId());
        BDDMockito.when(batchStockRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getBatchStockWithoutOrder()));
        BDDMockito.when(productRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getProduct()));
        BDDMockito.when(sectorRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getSector()));

        InBoundOrder order = TestUtilsGenerator.getOrderWithId();

        Assertions.assertThatThrownBy(()
                        -> inBoundOrderImpService.updateInBoundOrder(order))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Batch stock does not belong to this inbound order");
    }
}