package com.pluralsight;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FinancialTransactionsApp {

    // Creating variables that should remain the same throughout the entirety of the programs runtime
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String transactionFile = "transactions.csv";

    // Uses the TransactionList class to initialize a new transaction list array
    private static TransactionList transactionList = new TransactionList();
    private static Console console = new Console();


    // Application start point
    // Loads data and starts main loop, also handle errors
    public static void main(String[] args) {
        try {
            transactionList.loadTransactions(transactionFile);
            runApp();
        } catch (IOException e) {
            System.out.println("Error accessing transaction file:" + transactionFile + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Method that starts the interactive part of the application. Handles looping
    private static void runApp() throws IOException {
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

    // Displays the options the user has when first starting the app
    private static void displayHomeScreen() {
        System.out.println("\nHome screen:\n" +
                "\nD) Add Deposit" +
                "\nP) Make Payment" +
                "\nL) Ledger" +
                "\nX) Exit");
    }

    // Displays ledger screen and it's possible options
    private static void displayLedger() {
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
                    displayFilteredTransactions(true);
                    break;
                case "p":
                    displayFilteredTransactions(false);
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

    // Display the reports screen and it's options
    private static void displayReports() {
        while (true) {
            System.out.println("\nReports:" +
                    "\n1) Month To Date" +
                    "\n2) Previous Month" +
                    "\n3) Year To Date" +
                    "\n4) Previous Year" +
                    "\n5) Search by Vendor" +
                    "\n6) Custom Search" +
                    "\n0) Back to Ledger page");
            String choice = console.promptForString("Please make a selection (1, 2, 3, 4, 5, 0):");

            switch (choice.toLowerCase()) {
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
                case "6":
                    customSearch();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\nInvalid choice, please try again.");
            }
        }
    }

    // Methodology for adding a transaction (deposit or payment)
    // Uses a boolean (isDeposit) to ascertain how the deposit should be processed
    private static void addTransaction(boolean isDeposit) throws IOException {
        System.out.println("\n" + (isDeposit ? "Add deposit:" : "Make payment:"));
        LocalDateTime now = LocalDateTime.now();
        String description = console.promptForString("Enter " + (isDeposit ? "deposit" : "payment") + " description: ");
        String vendor = console.promptForString("Enter vendor: ");
        double amount = console.promptForFloat("Enter " + (isDeposit ? "deposit" : "payment") + " amount:");

        if (!isDeposit) {
            amount = -Math.abs(amount);

        } else {
            amount = -Math.abs(amount);
        }
        Transaction transaction = new Transaction(now, description, vendor, amount);
        transactionList.addTransaction(transaction);
        transactionList.saveTransactions(transactionFile);
        System.out.println((isDeposit ? "\nDeposit" : "\nPayment") + " added successfully.");
    }

    // Displays all transactions by iterating through the transactions list
    // Gets information directly from TransactionList.java
    private static void displayAllTransactions() {
        List<Transaction> transactions = transactionList.getTransactions();
        DisplayUtils.printFormattedList(transactions, "All Transactions:");

    }

    // Displays only deposits or only payments using the formatted list
    private static void displayFilteredTransactions(boolean showDeposits) {
        List<Transaction> allTransactions = transactionList.getTransactions();
        List<Transaction> filteredList = new ArrayList<>();
        String reportTitle = showDeposits ? "Deposits" : "Payments";

        for (Transaction transaction : allTransactions) {
            boolean isPositive = transaction.getAmount() > 0;
            if (showDeposits && isPositive) {
                filteredList.add(transaction);

            } else if (!showDeposits && !isPositive) {
                    filteredList.add(transaction);
            }
        }
        DisplayUtils.printFormattedList(filteredList, reportTitle);
    }

    //   === Report generation methods ===

    // Displays a month to date report
    // Includes all transactions from the start of the month to the current day
    private static void displayMonthToDate() {
        LocalDate today = LocalDate.now();
        LocalDate[] range = DateUtils.getMonthToDateRange(today);
        LocalDate startDate = range[0];
        LocalDate endDate = range[1];
        List<Transaction> transactions = transactionList.getTransactions(startDate, endDate);
        double total = transactionList.getTotalTransactions(startDate, endDate);

        String title = "\nMonth to date report (" + startDate.format(dateFormatter) + " - "
                + endDate.format(dateFormatter) + ")";

        DisplayUtils.printFormattedList(transactions, title);
        System.out.printf("Total: $%.2f%n", total);
    }

    // Displays all transactions from the previous month
    private static void displayPreviousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate[] range = DateUtils.getPreviousMonthRange(today);
        LocalDate startDate = range[0];
        LocalDate endDate = range[1];

        List<Transaction> transactions = transactionList.getTransactions(startDate, endDate);
        double total = transactionList.getTotalTransactions(startDate, endDate);

        String title = "\nPrevious month's report (" + startDate.format(dateFormatter) + " - "
                + endDate.format(dateFormatter);

        DisplayUtils.printFormattedList(transactions, title);
        System.out.printf("Total: $%.2f%n", total);
    }

    // Displays a year to date report
    // Includes all transactions from the start of the year to the current day
    private static void displayYearToDate() {
        LocalDate today = LocalDate.now();
        LocalDate[] range = DateUtils.getYearToDateRange(today);
        LocalDate startDate = range[0];
        LocalDate endDate = range[1];

        List<Transaction> transactions = transactionList.getTransactions(startDate, endDate);
        double total = transactionList.getTotalTransactions(startDate, endDate);

        String title = "\nYear to date report (" + startDate.format(dateFormatter)
                + " - " + endDate.format(dateFormatter) + ")";

        DisplayUtils.printFormattedList(transactions, title);
        System.out.printf("Total: $%.2f%n", total);
    }

    // Displays report of the previous year
    private static void displayPreviousYear() {

        LocalDate today = LocalDate.now();
        LocalDate[] range = DateUtils.getPrevYearRange(today);
        LocalDate startDate = range[0];
        LocalDate endDate = range[1];

        List<Transaction> transactions = transactionList.getTransactions(startDate, endDate);
        double total = transactionList.getTotalTransactions(startDate, endDate);

        String title = "\nPrevious year report (" + startDate.format(dateFormatter) + " - "
                + endDate.format(dateFormatter) + ")";

        DisplayUtils.printFormattedList(transactions, title);
        System.out.printf("Total: $%.2f%n", total);
    }

    // Displays transactions matching a vendor name entered by the user
    private static void searchByVendor() {
        // Allows the user to search for all transactions that come from a specific vendor
        String vendor = console.promptForString("\nEnter vendor name to search: ");
        List<Transaction> transactions = transactionList.getTransactionsByVendor(vendor);
        String title = "Transactions for Vendor '" + vendor + "'";

        DisplayUtils.printFormattedList(transactions, title);
    }

    //   === Custom search methods
    // Created a helper method that prompts the user for a date (optional) and parses it
    // Loops until given a valid date or a blank input
    private static LocalDate parseOptionalDate(String prompt) {
        while (true) {
            String input = console.promptForString(prompt);
            if (input.isBlank()) {
                return null;
            }
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use (yyyy-MM-dd) or leave blank.");
            }
        }
    }

    // Handles the custom search Interface
    // Prompts the user for optional criteria and displays matching transactions
    private static void customSearch() {
        System.out.println("\nCustom transaction search (leave blank to ignore a filter):");

        LocalDate startDate = parseOptionalDate("Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = parseOptionalDate("Ender end date (yyyy-MM-dd): ");
        String description = console.promptForString("Enter transaction description: ");
        String vendor = console.promptForString("Enter transaction vendor: ");

        List<Transaction> results = transactionList.searchTransactions(startDate, endDate, description, vendor);
        DisplayUtils.printFormattedList(results, "Custom search results");
    }
}