package com.project.bank.init;

import com.project.bank.service.ExRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ExchangeRateInitializer implements CommandLineRunner {
    private final ExRateService exRateService;

    public ExchangeRateInitializer(ExRateService exRateService) {
        this.exRateService = exRateService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!exRateService.hasInitializedExRate()){
            exRateService.updateRates(exRateService.fetchExRates());
        }
    }
}
