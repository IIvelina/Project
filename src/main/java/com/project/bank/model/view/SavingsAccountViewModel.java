package com.project.bank.model.view;

public class SavingsAccountViewModel {
    private String safetyDepositBox;
    private String safetyDepositKey;

    public SavingsAccountViewModel(String safetyDepositBox, String safetyDepositKey) {
        this.safetyDepositBox = safetyDepositBox;
        this.safetyDepositKey = safetyDepositKey;
    }

    public String getSafetyDepositBox() {
        return safetyDepositBox;
    }

    public void setSafetyDepositBox(String safetyDepositBox) {
        this.safetyDepositBox = safetyDepositBox;
    }

    public String getSafetyDepositKey() {
        return safetyDepositKey;
    }

    public void setSafetyDepositKey(String safetyDepositKey) {
        this.safetyDepositKey = safetyDepositKey;
    }
}
