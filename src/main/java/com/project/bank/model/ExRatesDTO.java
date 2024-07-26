package com.project.bank.model;

import java.math.BigDecimal;
import java.util.Map;

public record ExRatesDTO(String base, Map<String, BigDecimal> rates){

    /*

    {
  "disclaimer": "Usage subject to terms: https://openexchangerates.org/terms",
  "license": "https://openexchangerates.org/license",
  "timestamp": 1721397600,
  "base": "USD",
  "rates": {
  ...
    "BGN": 1.796386,
    ....
    "EUR": 0.918936,
   ...
  }
}
     */
}
