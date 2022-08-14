package com.desafiofinal.praticafinal.controller;

import com.desafiofinal.praticafinal.dto.requestResponseDto.BatchStockResponseDTO;
import com.desafiofinal.praticafinal.dto.ProductDTO;
import com.desafiofinal.praticafinal.model.BatchStock;
import com.desafiofinal.praticafinal.model.Product;
import com.desafiofinal.praticafinal.service.IBatchStockService;
import com.desafiofinal.praticafinal.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class holds all endpoints related to the product
 * @author Yago, Mônica, Marina
 * @version 1.0.0
 * @see Requirement 3 docs: https://br-playground.digitalhouse.com/course/86ba8e00-da33-420a-a62a-02d4a77c55e8/unit/f1c410da-fa91-44a9-b216-b6b93b85246c/lesson/6d242a1a-4961-4105-be9c-9189eb8eb6bc/topic/efd4119b-f679-48db-b77a-f7f18e0ca1c6
 */

@RestController
@RequestMapping("/api/v1/fresh-products/product")
public class ProductController {

    private final IProductService service;
    private final IBatchStockService batchStockService;
    public ProductController(IProductService service, IBatchStockService batchStockService) {
        this.service = service;
        this.batchStockService = batchStockService;
    }

    /**
     * This route creates a list of products alongside the seller information
     * @param product ProductName(String), ProductType(String), ValidateDate(LocalDate), Price(double), IdSeller(Long), Bulk(double).
     * @return HTML Response 201: Created
     */
    @PostMapping
    public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO productDTO){
        Product newProduct = ProductDTO.convertDtoToProduct(productDTO);
        Product response = service.saveProduct(newProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(response));
    }

    /**
     * This route lists all products
     * @return HTML Response 201: Created
     */
    @GetMapping("/products")
    ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> response = ProductDTO.convertToDTO(service.listAllProducts());
        return new ResponseEntity<List<ProductDTO>>(response, HttpStatus.FOUND);

    }
    
    /**
     * This route lists all products from a specific category
     * @param category String containing the category name
     * @return HTML Response 201: Created
     * @throws Exception 
     */
    @GetMapping("/{category}")
    ResponseEntity<List<BatchStockResponseDTO>> getProductBySector(@PathVariable String category)throws Exception{
        List<BatchStock> response = batchStockService.listBatchStockByCategory(category);
        return new ResponseEntity<List<BatchStockResponseDTO>>(BatchStockResponseDTO.convertToListDto(response),HttpStatus.FOUND);
    }
}
