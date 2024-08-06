package com.project.bank.service.impl;

import com.project.bank.config.ForexApiConfig;
import com.project.bank.model.dto.ExRatesDTO;
import com.project.bank.model.entity.ExRateEntity;
import com.project.bank.repository.ExRateRepository;
import com.project.bank.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExRateServiceImplTest2 {

    @Mock
    private ExRateRepository exRateRepository;

    @Mock
    private RestClient restClient;

    @Mock
    private ForexApiConfig forexApiConfig;

    @InjectMocks
    private ExRateServiceImpl exRateServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllSupportedCurrencies() {
        ExRateEntity exRateEntity1 = new ExRateEntity().setCurrency("USD");
        ExRateEntity exRateEntity2 = new ExRateEntity().setCurrency("EUR");

        when(exRateRepository.findAll()).thenReturn(List.of(exRateEntity1, exRateEntity2));

        List<String> supportedCurrencies = exRateServiceImpl.allSupportedCurrencies();

        assertEquals(List.of("USD", "EUR"), supportedCurrencies);
        verify(exRateRepository, times(1)).findAll();
    }

    @Test
    void testHasInitializedExRate() {
        when(exRateRepository.count()).thenReturn(1L);

        assertTrue(exRateServiceImpl.hasInitializedExRate());
        verify(exRateRepository, times(1)).count();
    }



    @Test
    void testUpdateRates() {
        ExRatesDTO exRatesDTO = new ExRatesDTO("USD", Map.of("EUR", BigDecimal.valueOf(0.85)));

        when(forexApiConfig.getBase()).thenReturn("USD");
        when(exRateRepository.findByCurrency("EUR")).thenReturn(Optional.empty());

        exRateServiceImpl.updateRates(exRatesDTO);

        ArgumentCaptor<ExRateEntity> exRateEntityCaptor = ArgumentCaptor.forClass(ExRateEntity.class);
        verify(exRateRepository, times(1)).save(exRateEntityCaptor.capture());

        ExRateEntity savedEntity = exRateEntityCaptor.getValue();
        assertEquals("EUR", savedEntity.getCurrency());
        assertEquals(BigDecimal.valueOf(0.85), savedEntity.getRate());
    }

    @Test
    void testFindExRate() {
        when(forexApiConfig.getBase()).thenReturn("USD");
        when(exRateRepository.findByCurrency("BGN")).thenReturn(Optional.of(new ExRateEntity().setCurrency("BGN").setRate(BigDecimal.valueOf(1.6))));
        when(exRateRepository.findByCurrency("EUR")).thenReturn(Optional.of(new ExRateEntity().setCurrency("EUR").setRate(BigDecimal.valueOf(0.85))));

        Optional<BigDecimal> rate = exRateServiceImpl.findExRate("BGN", "EUR");

        assertTrue(rate.isPresent());
        assertEquals(BigDecimal.valueOf(0.53), rate.get());
    }


    @Test
    void testConvertThrowsException() {
        when(forexApiConfig.getBase()).thenReturn("USD");
        when(exRateRepository.findByCurrency("BGN")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ObjectNotFoundException.class, () ->
                exRateServiceImpl.convert("BGN", "EUR", BigDecimal.valueOf(100)));

        assertEquals("Conversion from BGN to EUR not possible!", exception.getMessage());
    }
}