package org.example.model;

public class Transaction {
    private final String category;
    private final double amount;
    private final TransactionType type;

    public Transaction(String category, double amount, TransactionType type) {

        this.category = category;
        this.amount = amount;
        this.type = type;
    }
    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
}
