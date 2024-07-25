package com.project.bank.model.dto;

import com.project.bank.model.enums.AccountType;
import java.math.BigDecimal;
import java.util.List;

public record MyAccountDetailsDTO(Long id,
                                  AccountType type,
                                  BigDecimal balance,
                                  String clientNumber,
                                  List<String> currency) {
}
