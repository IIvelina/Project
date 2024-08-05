package com.project.bank.model.view;

public class CheckingAccountViewModel {
    private String debitCardNumber;
    private String debitCardPin;

    public CheckingAccountViewModel(String debitCardNumber, String debitCardPin) {
        this.debitCardNumber = debitCardNumber;
        this.debitCardPin = debitCardPin;
    }

    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    public void setDebitCardNumber(String debitCardNumber) {
        this.debitCardNumber = debitCardNumber;
    }

    public String getDebitCardPin() {
        return debitCardPin;
    }

    public void setDebitCardPin(String debitCardPin) {
        this.debitCardPin = debitCardPin;
    }
}
