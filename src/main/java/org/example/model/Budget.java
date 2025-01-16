package org.example.model;

public class Budget {
    private final String category;
    private final double limit;
    private double spent;

    public Budget(String category, double limit) {
        this.category = category;
        this.limit = limit;
        this.spent = 0.0;
    }
    public String getCategory() {
        return category;
    }
    public double getLimit() {
        return limit;
    }
    public double getSpent() {
        return spent;
    }
    public void addExpense(double amount) {

        this.spent += amount;
    }
    public double getRemaining() {
        return limit - spent;

    }
    public boolean isOverLimit() {
        return spent > limit;
    }
}
