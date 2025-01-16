package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
    private double balance;
    private final List<Transaction> transactions;
    private final Map<String, Budget> budgets;

    public Wallet() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.budgets = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Map<String, Budget> getBudgets() {
        return budgets;
    }
    public boolean transferToAnotherAccount(Wallet to, double amount) {
        if (balance >= amount ) {
            this.addTransaction(new Transaction("Transfers", amount,TransactionType.EXPENSE));
            to.addTransaction(new Transaction("Transfers", amount,TransactionType.INCOME));
            return true;
        } else {
            return false;

        }
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType() == TransactionType.INCOME) {
            balance += transaction.getAmount();
        } else {
            balance -= transaction.getAmount();
            budgets.computeIfPresent(transaction.getCategory(), (k, v) -> {
                v.addExpense(transaction.getAmount());
                return v;
            });
        }
    }

    public void addBudget(String category, double limit) {

        budgets.put(category, new Budget(category, limit));

    }

    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("Balance: ").append(balance).append("\n");
        sb.append("Transactions:\n");
        for (Transaction t : transactions) {
            sb.append("- ")
                    .append(t.getType())
                    .append(" ").append(t.getAmount())
                    .append(" in category ")
                    .append(t.getCategory()).append("\n");
        }
        sb.append("Budgets:\n");
        budgets.forEach((k, v) -> {
            sb.append("- ")
                    .append(k)
                    .append(": Limit ")
                    .append(v.getLimit())
                    .append(", Spent ")
                    .append(v.getSpent())
                    .append(", Remaining ")
                    .append(v.getRemaining())
                    .append("\n");
            if (v.isOverLimit()) {
                sb.append("Budget limit exceeded!").append("\n");
            }
        });

        return sb.toString();
    }
}

