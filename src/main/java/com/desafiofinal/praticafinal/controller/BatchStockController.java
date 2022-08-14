package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.dto.queryDto.*;
import com.desafiofinal.praticafinal.repository.IBatchStockRepo;
import com.desafiofinal.praticafinal.service.BatchStockImpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class holds all endpoints related to fillter and display products
 * @author Yago, Mônica
 * @version 1.0.0
 * @see Requirement 3, 4 and 5 docs: https://br-playground.digitalhouse.com/course/86ba8e00-da33-420a-a62a-02d4a77c55e8/unit/f1c410da-fa91-44a9-b216-b6b93b85246c/lesson/6d242a1a-4961-4105-be9c-9189eb8eb6bc/topic/efd4119b-f679-48db-b77a-f7f18e0ca1c6
 */
@RestController
@RequestMapping("/api/v1/fresh-products/sectorProducts")
public class BatchStockController {

    @Autowired
    private BatchStockImpService service;

    @Autowired
    private IBatchStockRepo repo;

    /**
     * This route lists the products by id.
     * @param productId A long.
     * @return HTML Response 200: OK
     */
    @GetMapping("/{productId}")
    ResponseEntity<List<ResponseSectorQuery>> getBatchSector(@PathVariable long productId) {
        List<ResponseSectorQuery> getResponse = service.listBatchSector(productId);
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }
    /**
     * This route lists the products by id and alphabetic order.
     * @param productId A long.
     * @param string Recives Q and orders by the actual quantity - L and orders by batch  - V and order by the product due date.
     * @return HTML response 200: OK
     * @throws Exception ElementAlreadyExistsException
     */
    @GetMapping("/{productId}/{string}")
    ResponseEntity <List<ResponseSectorQuery>> getBatchSectorOrdered(@PathVariable long productId, @PathVariable String string) throws Exception {
        List<ResponseSectorQuery> getResponse = service.listBatchSectorOrdered(productId, string);
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }
    /**
     * This route lists a specific product quantity
     * @param productId A long.
     * @return HTML response 200: OK
     */
    @GetMapping("sector/{productId}")
    ResponseEntity<ResponseSectorTotalQuantity> getTotalQuantitySector(@PathVariable long productId)  {
        ResponseSectorTotalQuantity getResponse = service.getTotalQuantity(productId);
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }
    /**
     * This route lists due dates of a specific sector
     * @param sectorId A long. 
     * @param days A long.
     * @return HTML status 200: OK
     */
    @GetMapping("stockByDays/{sectorId}/{days}")
    ResponseEntity<ResponseStock> getStockByDueDate(@PathVariable Long sectorId, @PathVariable Long days)  {
       ResponseStock getResponse = service.getListDueDate(sectorId, days);
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }
    /**
     * This route lists due dates of a specific product category
     * @param category A String.
     * @param days A long.
     * @return HTML status 200: ok
     */
    @GetMapping("stockByCategoryDays/{category}/{days}")
    ResponseEntity<ResponseStock> getCategoryDueDate(@PathVariable String category, @PathVariable Long days)  {
        ResponseStock getResponse = service.getListCategoryDueDate(category,days);
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }
}