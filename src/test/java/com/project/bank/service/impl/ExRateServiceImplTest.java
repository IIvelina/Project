package com.project.bank.service.impl;

import com.project.bank.config.ForexApiConfig;
import com.project.bank.exception.ObjectNotFoundException;

import com.project.bank.model.entity.ExRateEntity;
import com.project.bank.repository.ExRateRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExRateServiceImplTest {

    private static final class TestRates {
        private static final String BASE_CURRENCY = "SUD";

        private static final ExRateEntity CUR1 = new ExRateEntity()
                .setCurrency("CUR1").setRate(new BigDecimal("4"));

        private static final ExRateEntity CUR2 = new ExRateEntity()
                .setCurrency("CUR2").setRate(new BigDecimal("0.5"));
    }

    private ExRateServiceImpl toTest;

    @Mock
    private ExRateRepository mockRepository;

    @Mock
    private RestClient restClient;

    @BeforeEach
    void setUp() {
        ForexApiConfig forexApiConfig = new ForexApiConfig();
        forexApiConfig.setBase(TestRates.BASE_CURRENCY);

        toTest = new ExRateServiceImpl(mockRepository, restClient, forexApiConfig);
    }

    @ParameterizedTest(name = "Converting {2} {0} to {1}. Expected {3}")
    @CsvSource({
            "SUD, CUR1, 1, 4.00",
            "SUD, CUR1, 2, 8.00",
            "SUD, SUD,  1, 1",
            "CUR1,CUR2, 1, 0.12",
            "CUR2,CUR1, 1, 8.00",
            "LBD, LBD, 1, 1"
    })
    void testConvert(String from, String to, BigDecimal amount, BigDecimal expected) {
        initExRates();

        BigDecimal result = toTest.convert(from, to, amount);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testObjectNotFoundException() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> toTest.convert("NO_EXIST_1", "NOT_EXIST_2", BigDecimal.ONE)
        );
    }

    @Test
    void testHasInitializedExRates() {
        when(mockRepository.count()).thenReturn(0L);
        Assertions.assertFalse(toTest.hasInitializedExRate());

        when(mockRepository.count()).thenReturn(-5L);
        Assertions.assertFalse(toTest.hasInitializedExRate());

        when(mockRepository.count()).thenReturn(6L);
        Assertions.assertTrue(toTest.hasInitializedExRate());
    }

    private void initExRates() {
        when(mockRepository.findByCurrency(TestRates.CUR1.getCurrency()))
                .thenReturn(Optional.of(TestRates.CUR1));
        when(mockRepository.findByCurrency(TestRates.CUR2.getCurrency()))
                .thenReturn(Optional.of(TestRates.CUR2));
    }
}