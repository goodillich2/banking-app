package com.example.bankingapp1.dto;


import java.math.BigDecimal;

public class TransferDTO {

    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;

    // Getters and Setters
    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

