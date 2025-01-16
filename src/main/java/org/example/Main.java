package org.example;

import org.example.model.User;
import org.example.services.UserService;
import org.example.utils.FileUtil;
import org.example.model.Transaction;
import org.example.model.TransactionType;

import java.util.Scanner;

public class Main {
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        userService.setUsers(FileUtil.loadUsers());

        while (true) {
            System.out.println("\nHELLO PLEASE ADD FOLLOWING COMMANDS: ");
            System.out.println("Register | Login | Logout | AddIncome | AddExpense | AddBudget | ShowStats | Transfer | Exit");
            System.out.print("Enter your choice: ");

            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "register":
                    registerUser();
                    break;
                case "login":
                    loginUser();
                    break;
                case "logout":
                    logoutUser();
                    break;
                case "addincome":
                    addIncome();
                    break;
                case "addexpense":
                    addExpense();
                    break;
                case "addbudget":
                    addBudget();
                    break;
                case "showstats":
                    showStats();
                    break;
                case "transfer":
                    moneyTransfer();
                    break;
                case "exit":
                    FileUtil.saveUsers(userService.getUsers());
                    System.out.println("Bye bye! See you next time!");
                    System.exit(0);
                default:
                    System.out.println("Unknown command, please try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Add username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Add password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty!");
            return;
        }

        if (userService.registerUser(username, password)) {
            FileUtil.saveUsers(userService.getUsers());
            System.out.println("User has been registered.");
        } else {
            System.out.println("User already exists.");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (userService.login(username, password)) {
            System.out.println("Welcome " + username + "!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void logoutUser() {
        userService.logout();
        System.out.println("You have been logged out.");
    }

    private static void addIncome() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("You need to log in first.");
            return;
        }

        System.out.print("Enter income category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter income amount: ");
        double amount = getValidDoubleInput();

        currentUser.getWallet().addTransaction(new Transaction(category, amount, TransactionType.INCOME));
        FileUtil.saveUsers(userService.getUsers());
        System.out.println("Your income has been added.");
    }
    private static void moneyTransfer() {
        User sender = userService.getCurrentUser();
        if (sender == null) {
            System.out.println("You need to log in first.");
            return;
        }
        System.out.println("Add the username who you want to transfer: ");
        String userReceiver = scanner.nextLine().trim();

        User receiver = userService.getUsers().get(userReceiver);
        if (receiver == null) {
            System.out.println("There is no user with that name.");
            return;
        }
        System.out.println("Enter the amount you want to transfer: ");
        double amount = getValidDoubleInput();

        if (sender.getWallet().transferToAnotherAccount(receiver.getWallet(),amount)){
            FileUtil.saveUsers(userService.getUsers());
            System.out.println("You money have been transferred to: " + userReceiver);
        } else {
            System.out.println("You do not have enough money to transfer.");
        }
    }

    private static void addExpense() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("You need to log in first.");
            return;
        }

        System.out.print("Enter expense category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter expense amount: ");
        double amount = getValidDoubleInput();

        currentUser.getWallet()
                .addTransaction(new Transaction(category, amount, TransactionType.EXPENSE));
        FileUtil.saveUsers(userService.getUsers());
        System.out.println("Your expense has been added.");
    }

    private static void addBudget() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("You need to log in first.");
            return;
        }

        System.out.print("Enter budget category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter budget limit: ");
        double limit = getValidDoubleInput();

        currentUser.getWallet().addBudget(category, limit);
        FileUtil.saveUsers(userService.getUsers());
        System.out.println("Your budget has been added.");
    }

    private static void showStats() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("You need to log in first.");
            return;
        }

        System.out.println(currentUser.getWallet().getStatistics());
    }

    private static double getValidDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number! Please enter a valid amount:");
            }
        }
    }
}
