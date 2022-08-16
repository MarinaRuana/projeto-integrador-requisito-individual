package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.exception.ElementNotFoundException;
import com.desafiofinal.praticafinal.model.Product;
import com.desafiofinal.praticafinal.repository.IBatchStockRepo;
import com.desafiofinal.praticafinal.repository.IProductRepo;
import com.desafiofinal.praticafinal.repository.ISellerRepo;
import com.desafiofinal.praticafinal.utils.TestUtilsGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductImplServiceTest {

    @InjectMocks
    ProductImplService productService;

    @Mock
    IProductRepo productRepo;

    @Mock
    ISellerRepo sellerRepo;


    @Test
    void saveProduct_whenSellerExists() {
        BDDMockito.when(sellerRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(TestUtilsGenerator.getSeller()));
        BDDMockito.when(productRepo.save(ArgumentMatchers.any(Product.class)))
                .thenReturn(TestUtilsGenerator.getProduct());

        Product newProduct = TestUtilsGenerator.getNewProduct();

        Product savedProduct = productService.saveProduct(newProduct);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo(newProduct.getProductName());
        assertThat(savedProduct.getProductType()).isEqualTo(newProduct.getProductType());

    }

    @Test
    void saveProduct_throwException_whenSellerNotExists() {
        BDDMockito.when(productRepo.save(ArgumentMatchers.any(Product.class)))
                .thenReturn(TestUtilsGenerator.getProduct());

        Product newProduct = TestUtilsGenerator.getNewProduct();

        Assertions.assertThatThrownBy(()
                        -> productService.saveProduct(newProduct))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Seller does not exist");
    }

    @Test
    void listAllProducts() {
        BDDMockito.when(productRepo.findAll())
                .thenReturn(TestUtilsGenerator.getProductList());

        List<Product> productList = productService.listAllProducts();

        assertThat(productList).isNotNull();
        assertThat(productList.get(0).getProductName()).isEqualTo("ham");
        assertThat(productList.get(0).getProductType()).isEqualTo("cold");
        assertThat(productList.size()).isEqualTo(1);
        Mockito.verify(productRepo, Mockito.atLeastOnce()).findAll();

    }
}