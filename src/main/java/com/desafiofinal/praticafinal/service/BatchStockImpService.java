package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.dto.queryDto.*;

import com.desafiofinal.praticafinal.exception.ElementAlreadyExistsException;
import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.repository.IBatchStockRepo;
import com.desafiofinal.praticafinal.repository.IProductRepo;
import com.desafiofinal.praticafinal.repository.InBoundOrderRepo;
import org.springframework.stereotype.Service;
import com.desafiofinal.praticafinal.model.BatchStock;
import com.desafiofinal.praticafinal.model.InBoundOrder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchStockImpService implements IBatchStockService {

    private final InBoundOrderRepo inBoundOrderRepo;

    private final IProductRepo productRepo;

    private final IBatchStockRepo batchStockRepo;
    public BatchStockImpService(InBoundOrderRepo inBoundOrderRepo, IProductRepo productRepo, IBatchStockRepo batchStockRepo) {
        this.inBoundOrderRepo = inBoundOrderRepo;
        this.productRepo = productRepo;
        this.batchStockRepo = batchStockRepo;
    }

    @Override
    public List<BatchStock> listBatchStockByCategory (String category) {

        List<InBoundOrder> listInBoundOrder = inBoundOrderRepo.findAll();
        List<BatchStock> batchListByCategory = new ArrayList<>();

        for (InBoundOrder inBoundOrder: listInBoundOrder){
            verifyDueDatePerCategory(category, batchListByCategory, inBoundOrder);
        }
        if(batchListByCategory.isEmpty()){
            throw new ElementAlreadyExistsException("No products were found for this category");
        }else {
            return batchListByCategory;
        }
    }

    public List<ResponseSectorQuery> listBatchSector(long id) {

        List<DataBaseQuery> listBatchSector = batchStockRepo.getListBatchSector(id);

        if (listBatchSector.isEmpty()) {
            throw new ElementNotFoundException("Não há lote de produtos com esse id");
        }

        return buildResponseQueryList(listBatchSector);
    }

    public List<ResponseSectorQuery> listBatchSectorOrdered(long id, String string) {
        List<ResponseSectorQuery> responseSectorQueryList;
        List<DataBaseQuery> dataBaseQuery;

        switch (string) {
            case "l":
            case "L":
                dataBaseQuery = batchStockRepo.getListOrderedById(id);
                responseSectorQueryList = buildResponseQueryList(dataBaseQuery);
                break;

            case "q":
            case "Q":
                dataBaseQuery = batchStockRepo.getListOrderedByQuantity(id);
                responseSectorQueryList = buildResponseQueryList(dataBaseQuery);
                break;

            case "v":
            case "V":
                dataBaseQuery = batchStockRepo.getListOrderedByDueDate(id);
                responseSectorQueryList = buildResponseQueryList(dataBaseQuery);
                break;

            default:
                throw new ElementAlreadyExistsException("Essa opção de ordenação não existe");
        }
        return responseSectorQueryList;
    }

    private void verifyDueDatePerCategory(String category, List<BatchStock> batchListByCategory, InBoundOrder inBoundOrder) {
        String foundCategory = inBoundOrder.getSector().getCategory();

        if(foundCategory.equalsIgnoreCase(category)){
            for (BatchStock batchStock: inBoundOrder.getBatchStockList()){
                LocalDate minusDays2 = batchStock.getDueDate().minusDays(21);
                if(LocalDate.now().isBefore(minusDays2)){
                    batchListByCategory.add(batchStock);
                }
            }
        }
    }

    private List<ResponseSectorQuery> buildResponseQueryList(List<DataBaseQuery> listBatchSector) {
        List<ResponseSectorQuery> responseSectorQueryList = new ArrayList<>();
        List<SectorQuery> sectorQueryList = new ArrayList<>();
        List<StockQuery> stockQueryList = new ArrayList<>();

        buildInitialList(listBatchSector, sectorQueryList, stockQueryList);
        filterBySector(responseSectorQueryList, sectorQueryList, stockQueryList);

        return responseSectorQueryList;
    }

    private void buildInitialList(List<DataBaseQuery> listBatchSector, List<SectorQuery> sectorQueryList, List<StockQuery> stockQueryList) {
        for (DataBaseQuery batchStockSectorDTO : listBatchSector) {
            StockQuery stockQuery = StockQuery.builder()
                    .batchId(batchStockSectorDTO.getBatch_id())
                    .currentQuantity(batchStockSectorDTO.getCurrent_quantity())
                    .dueDate(batchStockSectorDTO.getDue_date())
                    .sectorId(batchStockSectorDTO.getSector_id())
                    .productId(batchStockSectorDTO.getId_product())
                    .build();
            stockQueryList.add(stockQuery);

            SectorQuery sectorQuery = new SectorQuery();
            sectorQuery.setCategory(batchStockSectorDTO.getCategory());
            sectorQuery.setSectorId(batchStockSectorDTO.getSector_id());

            if (!sectorQueryList.contains(sectorQuery)) {
                sectorQueryList.add(sectorQuery);
            }
        }
    }

    private void filterBySector(List<ResponseSectorQuery> responseSectorQueryList, List<SectorQuery> sectorQueryList, List<StockQuery> stockQueryList) {
        ResponseSectorQuery responseSectorQuery;

        for (SectorQuery sector : sectorQueryList) {
            List<StockQuery> responseStock = stockQueryList.stream().filter(stock -> stock.getSectorId() == sector.getSectorId()).collect(Collectors.toList());

            responseSectorQuery = ResponseSectorQuery.builder()
                    .sector(sector)
                    .productId(responseStock.get(0).getProductId())
                    .stockList(responseStock)
                    .build();

            responseSectorQueryList.add(responseSectorQuery);
        }
    }

    public ResponseSectorTotalQuantity getTotalQuantity(long id) {

        List<DataBaseTotalQuantityQuery> dataBaseTotalQuantityQueryList = batchStockRepo.getListQuantity(id);
        if(dataBaseTotalQuantityQueryList.isEmpty()){
            throw new ElementNotFoundException("Não há esse produto em nenhum depósito");
        }
        ResponseSectorTotalQuantity response = new ResponseSectorTotalQuantity();
        response.setProductId(id);
        buildResponse(dataBaseTotalQuantityQueryList, response);

        return response;
    }

    private void buildResponse(List<DataBaseTotalQuantityQuery> dataBaseTotalQuantityQueryList, ResponseSectorTotalQuantity response) {
        List<SectorQuantityQuery> sectorList = new ArrayList<>();

        for(DataBaseTotalQuantityQuery data: dataBaseTotalQuantityQueryList){
            SectorQuantityQuery sectorQuantity = SectorQuantityQuery.builder()
                                .sectorId(data.getSector_id())
                                .totalQuantity(data.getTotal_quantity())
                                .build();
            sectorList.add(sectorQuantity);
        }
        response.setSectorList(sectorList);
    }

    public ResponseStock getListDueDate (Long sectorId, Long days) {

        List<DataBaseStockQuery> listDueDate = batchStockRepo.getListDueDate(sectorId);
        List<ResponseStockQuery> listResponse = new ArrayList<>();

        if(listDueDate.isEmpty()){
            throw new ElementNotFoundException("Lista vazia");
        }

        verifyDueDate(days, listDueDate, listResponse);
        ResponseStock responseStock = new ResponseStock();
        if (listResponse.isEmpty()){
            throw new ElementNotFoundException("Nenhum item dentro da validade nesse intervalo de dias");
        }

        responseStock.setDataBaseStocks(listResponse);
        return responseStock;
    }

    public ResponseStock getListCategoryDueDate (String category, Long days) {

        List<DataBaseStockQuery> listDueDate = batchStockRepo.getListCategory(category);
        List<ResponseStockQuery> listResponse = new ArrayList<>();

        if(listDueDate.isEmpty()){
            throw new ElementNotFoundException("Lista vazia");
        }

        verifyDueDate(days, listDueDate, listResponse);
        ResponseStock responseStock = new ResponseStock();
        if (listResponse.isEmpty()){
            throw new ElementNotFoundException("Nenhum item dentro da validade nesse intervalo de dias");
        }

        responseStock.setDataBaseStocks(listResponse);
        return responseStock;
    }


    private void verifyDueDate(Long days, List<DataBaseStockQuery> listDueDate, List<ResponseStockQuery> listResponse) {
        for (DataBaseStockQuery data : listDueDate) {
            LocalDate minusDays2 = data.getDue_date().minusDays(days);

                if (LocalDate.now().isBefore(minusDays2)) {
                ResponseStockQuery stock = ResponseStockQuery.builder()
                        .batchId(data.getBatch_id())
                        .dueDate(data.getDue_date())
                        .idProduct(data.getId_product())
                        .productType(data.getProduct_type())
                        .currentQuantity(data.getCurrent_quantity())
                        .build();
                listResponse.add(stock);
            }
        }
    }


}


