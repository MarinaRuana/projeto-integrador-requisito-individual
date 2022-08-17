package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.dto.queryDto.DataBaseQueryImp;
import com.desafiofinal.praticafinal.dto.queryDto.ResponseSectorQuery;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;

import com.desafiofinal.praticafinal.model.BatchStock;
import com.desafiofinal.praticafinal.repository.*;
import com.desafiofinal.praticafinal.utils.TestUtilsGenerator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BatchStockImpServiceTest {

    @InjectMocks
    BatchStockImpService batchStockImpService;

    @Mock
    CartRepo cartRepo;

    @Mock
    IBatchStockRepo batchStockRepo;

    @Mock
    PurchaseRepo purchaseRepo;

    @Mock
    BuyerRepo buyerRepo;

    @Mock
    IProductRepo productRepo;

    @Mock
    ISectorRepo sectorRepo;

    @Mock
    InBoundOrderRepo inBoundOrderRepo;

    @Test
    void listBatchStockByCategory_whenHasProductInCategory() {
        BDDMockito.when(inBoundOrderRepo.findAll())
                .thenReturn(TestUtilsGenerator.getOrderList());

        List<BatchStock> batchStockList = batchStockImpService.listBatchStockByCategory("FF");

        assertThat(batchStockList).isNotNull();
        assertThat(batchStockList.size()).isEqualTo(3);
        Mockito.verify(inBoundOrderRepo, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void listBatchStockByCategory_throwsException_whenProductInCategoryNotExists() {

        Assertions.assertThatThrownBy(()
                        -> batchStockImpService.listBatchStockByCategory("FF"))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("No products were found for this category");
    }

    @Test
    void listBatchSector_whenListExists() {
        BDDMockito.when(batchStockRepo.getListBatchSector(ArgumentMatchers.anyLong()))
                .thenReturn(TestUtilsGenerator.getListDataBaseQuery());

        DataBaseQueryImp newBatch = TestUtilsGenerator.getDataBaseQueryImp();
        List<ResponseSectorQuery> newList = TestUtilsGenerator.getListResponseSectorQuery (newBatch.getId_product());
        List<ResponseSectorQuery> foundList = batchStockImpService.listBatchSector(newBatch.getId_product());

        assertThat(foundList).isNotNull();
        assertThat(foundList).isEqualTo(newList);
        assertThat(foundList.size()).isEqualTo(1);
    }

    @Test
    void listBatchSector_whenListDoesNotExist() {
        DataBaseQueryImp newBatch = TestUtilsGenerator.getDataBaseQueryImpWithOutBatch();

        assertThatThrownBy(() -> {
            batchStockImpService.listBatchSector(newBatch.getId_product());
        }).isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Não há lote de produtos com esse id");

    }

    @Test
    void listBatchSectorOrdered() {

    }

    @Test
    void getTotalQuantity() {
    }

    @Test
    void getListDueDate() {
    }

    @Test
    void getListCategoryDueDate() {
    }
}