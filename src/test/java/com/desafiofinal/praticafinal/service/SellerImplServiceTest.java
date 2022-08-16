package com.desafiofinal.praticafinal.service;

import com.desafiofinal.praticafinal.model.Seller;
import com.desafiofinal.praticafinal.repository.ISellerRepo;
import com.desafiofinal.praticafinal.utils.TestUtilsGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SellerImplServiceTest {

    @InjectMocks
    SellerImplService sellerImplService;

    @Mock
    ISellerRepo sellerRepo;

    @Test
    void saveSeller() {
        BDDMockito.when(sellerRepo.save(ArgumentMatchers.any(Seller.class)))
                .thenReturn(TestUtilsGenerator.getSeller());

        Seller newSeller = TestUtilsGenerator.getNewSeller();
        Seller savedSeller = sellerImplService.saveSeller(newSeller);

        assertThat(savedSeller).isNotNull();
        assertThat(savedSeller.getSellerName()).isEqualTo(newSeller.getSellerName());
    }

}