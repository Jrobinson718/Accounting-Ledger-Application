package com.pluralsight;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FinancialTransactionsApp {

    // Creating variables that should remain the same throughout the entirety of the programs runtime.
    private static final DateTimeFormatter DtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String transactionFile = "transactions.csv";

    // Uses the TransactionList class to initialize a new transaction list array
    private static TransactionList transactionList = new TransactionList();
    private static Console console = new Console();

    public static void main(String[] args) {
        try {
            // Main method that loads info from transactions.csv and runs app
            transactionList.loadTransactions(transactionFile);
            runApp();
        } catch (IOException e) {
            System.out.println("Error accessing transaction file:" + transactionFile + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void runApp() throws IOException {
        // Method that starts the interactive part of the application. Handles looping.
        while (true) {
            displayHomeScreen();
            String choice = console.promptForString("Please make a selection (D, P, L, X): ");
            switch (choice.toLowerCase()) {
                case "d":
                    addTransaction(true);
                    break;
                case "p":
                    addTransaction(false);
                    break;
                case "l":
                    displayLedger();
                    break;
                case "x":
                    System.out.println("\nExiting application.");
                    transactionList.saveTransactions(transactionFile);
                    return;
                default:
                    System.out.println("\nInvalid choice, please try again.");
            }
        }
    }

    private static void displayHomeScreen() {
        // Displays the options the user has when first starting the app
        System.out.println("\nHome screen:\n" +
                "\nD) Add Deposit" +
                "\nP) Make Payment" +
                "\nL) Ledger" +
                "\nX) Exit");
    }

    private static void addTransaction(boolean isDeposit) throws IOException {
        // Methodology for adding a transaction (deposit or payment)
        // Uses a boolean (isDeposit) to ascertain how the deposit should be processed
        System.out.println("\n" + (isDeposit ? "Add deposit:" : "Make payment:"));
        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.format(DtFormatter);
        String description = console.promptForString("Enter " + (isDeposit ? "deposit" : "payment") + " description: ");
        String vendor = console.promptForString("Enter vendor: ");
        double amount = console.promptForFloat("Enter " + (isDeposit ? "deposit" : "payment") + " amount:");

        if (!isDeposit) {
            amount = -Math.abs(amount);

        }
        Transaction transaction = new Transaction(now, description, vendor, amount);
        transactionList.addTransaction(transaction);
        transactionList.saveTransactions(transactionFile);
        System.out.println((isDeposit ? "\nDeposit" : "\nPayment") + " added successfully.");
    }

    private static void displayLedger() throws IOException {
        // Displays ledger screen and it's possible options
        while (true) {
            System.out.println("\nLedger:\n" +
                    "\nA) All - Display entire log" +
                    "\nD) Deposits - Only display deposits" +
                    "\nP) Payments - Only display payments" +
                    "\nR) Reports - Display reports" +
                    "\nH) Home - Go back to the home page");
            String choice = console.promptForString("Please make a selection (A, D, P, R, H):");
            switch (choice.toLowerCase()) {
                case "a":
                    displayAllTransactions();
                    break;
                case "d":
                    displayDeposits();
                    break;
                case "p":
                    displayPayments();
                    break;
                case "r":
                    displayReports();
                    break;
                case "h":
                    return;
                default:
                    System.out.println("\nInvalid choice, please try again.");
            }
        }
    }

    private static void displayAllTransactions() {
        // Displays all transactions by iterating through the transactions list
        // Gets information directly from TransactionList.java
        List<Transaction> transactions = transactionList.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions to display.");
            return;

        }
        System.out.println("\nAll transactions:");
        //Displays newest transactions first by iterating through the list backwards
        for (int i = transactions.size() - 1; i >= 0; i--) {
            System.out.println(transactions.get(i));

        }
    }

    private static void displayDeposits() {
        // Only display deposit transactions by checking to see if amount > 0
        List<Transaction> transactions = transactionList.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions to display.");
            return;

        }
        System.out.println("\nDeposits:\n");
        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
                found = true;

            }
        }
        if (!found) {
            System.out.println("\nNo deposits to display.");

        }
    }

    private static void displayPayments() {
        // Only displays payment transactions by checking to see if the amount < 0
        List<Transaction> transactions = transactionList.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("\nNo Transactions to display");
            return;

        }
        System.out.println("\nPayments:\n");
        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
                found = true;

            }
        }
        if (!found) {
            System.out.println("\nNo payments to display.");

        }
    }

    private static void displayReports() throws IOException {
        // Display the reports screen and it's options
        while (true){
            System.out.println("\nReports:" +
                    "\n1) Month To Date" +
                    "\n2) Previous Month" +
                    "\n3) Year To Date" +
                    "\n4) Previous Year" +
                    "\n5) Search by Vendor" +
                    "\n0) Back to Ledger page");
            String choice = console.promptForString("Please make a selection (1, 2, 3, 4, 5, 0):");

            switch (choice.toLowerCase()){
                case "1":
                    displayMonthToDate();
                    break;
                case "2":
                    displayPreviousMonth();
                    break;
                case "3":
                    displayYearToDate();
                    break;
                case "4":
                    displayPreviousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    displayLedger();
                    break;
                default:
                    System.out.println("\nInvalid choice, please try again.");
            }
        }

    }

    private static void displayMonthToDate() throws IOException {
        // Displays a month to date report
        // Includes all transactions from the start of the month to the current day
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        List<Transaction> transactions = transactionList.getTransactions(startOfMonth, today);

        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions to display.");
            return;

        }

        double total = transactionList.getTotalTransactions(startOfMonth, today);
        System.out.println("\nMonth to date report (" + startOfMonth.format(dateFormatter) + " - "
                + today.format(dateFormatter) + "):");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);


        }
        System.out.printf("Total: $%.2f\n", total);
    }

    private static void displayPreviousMonth() throws IOException {
        // Displays all transactions from the previous month
        LocalDate today = LocalDate.now();
        LocalDate startOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate endOfLastMonth = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());
        List<Transaction> transactions = transactionList.getTransactions(startOfLastMonth, endOfLastMonth);

        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions to display.");
            return;

        }

        double total = transactionList.getTotalTransactions(startOfLastMonth, endOfLastMonth);
        System.out.println("\nPrevious month's report (" + startOfLastMonth.format(dateFormatter) + " - "
                + endOfLastMonth.format(dateFormatter));
        for (Transaction transaction : transactions) {
            System.out.println(transaction);

        }
        System.out.printf("Total $%.2f\n", total);
    }

    private static void displayYearToDate() throws IOException {
        // Displays a year to date report
        // Includes all transactions from the start of the year to the current day
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.withDayOfYear(1);
        List<Transaction> transactions = transactionList.getTransactions(startOfYear, today);

        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions to display.");

        }

        double total = transactionList.getTotalTransactions(startOfYear, today);
        System.out.println("\nYear to date report (" + startOfYear.format(dateFormatter)
                + " - " + today.format(dateFormatter) + "):");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);

        }
        System.out.printf("Total: $%.2f\n", total);
    }

    private static void displayPreviousYear() throws IOException {
        // Displays report of the previous year
        LocalDate today = LocalDate.now();
        LocalDate startOfPreviousYear = today.minusYears(1).withDayOfYear(1);
        LocalDate endOfPreviousYear = today.minusYears(1).withDayOfYear(today.minusYears(1).lengthOfYear());
        List<Transaction> transactions = transactionList.getTransactions(startOfPreviousYear, endOfPreviousYear);

        if (transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;

        }

        double total = transactionList.getTotalTransactions(startOfPreviousYear, endOfPreviousYear);
        System.out.println("\nPrevious year report (" + startOfPreviousYear.format(dateFormatter) + " - "
                + endOfPreviousYear.format(dateFormatter) + "):");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.printf("Total: $%.2f\n", total);
    }

    private static void searchByVendor() throws IOException {
        // Allows the user to search for all transactions that come from a specific vendor
        String vendor = console.promptForString("Enter vendor name to search: ");
        List<Transaction> transactions = transactionList.getTransactionsByVendor(vendor);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found for vendor '" + vendor + "',");
            return;
        }
        System.out.println("\nTransactions for vendor '" + vendor + "'.");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}