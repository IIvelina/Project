package com.project.bank.web;

import com.project.bank.model.dto.ConversionResultDTO;
import com.project.bank.service.ExRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CurrencyControllerTest {

    @Mock
    private ExRateService exRateService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvert_SameCurrency() {
        String from = "USD";
        String to = "USD";
        BigDecimal amount = new BigDecimal("100");

        when(exRateService.convert(from, to, amount)).thenReturn(amount);

        ResponseEntity<ConversionResultDTO> response = currencyController.convert(from, to, amount);
        ConversionResultDTO conversionResult = response.getBody();

        assertEquals(ResponseEntity.ok(new ConversionResultDTO(from, to, amount, amount)), response);
        assertEquals(from, conversionResult.from());
        assertEquals(to, conversionResult.to());
        assertEquals(amount, conversionResult.amount());
        assertEquals(amount, conversionResult.result());

        verify(exRateService, times(1)).convert(from, to, amount);
    }

    @Test
    void testConvert_InvalidCurrency() {
        String from = "INVALID";
        String to = "USD";
        BigDecimal amount = new BigDecimal("100");

        when(exRateService.convert(from, to, amount)).thenThrow(new RuntimeException("Invalid currency"));

        try {
            currencyController.convert(from, to, amount);
        } catch (RuntimeException e) {
            assertEquals("Invalid currency", e.getMessage());
        }

        verify(exRateService, times(1)).convert(from, to, amount);
    }

    @Test
    void testConvert_ValidCurrency() {
        String from = "USD";
        String to = "EUR";
        BigDecimal amount = new BigDecimal("100");
        BigDecimal convertedAmount = new BigDecimal("85");

        when(exRateService.convert(from, to, amount)).thenReturn(convertedAmount);

        ResponseEntity<ConversionResultDTO> response = currencyController.convert(from, to, amount);
        ConversionResultDTO conversionResult = response.getBody();

        assertEquals(ResponseEntity.ok(new ConversionResultDTO(from, to, amount, convertedAmount)), response);
        assertEquals(from, conversionResult.from());
        assertEquals(to, conversionResult.to());
        assertEquals(amount, conversionResult.amount());
        assertEquals(convertedAmount, conversionResult.result());

        verify(exRateService, times(1)).convert(from, to, amount);
    }
}