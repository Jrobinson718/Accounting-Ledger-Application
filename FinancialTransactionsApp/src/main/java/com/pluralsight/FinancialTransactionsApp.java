package com.pluralsight;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class FinancialTransactionsApp {

    // Creating variables that should remain the same throughout the entirety of the programs runtime.
    private static final DateTimeFormatter DtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String transactionFile = "transactions.csv";

    // Uses the TransactionList class to initialize a new transaction list array
    private static TransactionList transactionList = new TransactionList();
    private static Console console = new Console();

    public static void main(String[] args) {
        try{
            // Main method that loads info from transactions.csv and runs app
            transactionList.loadTransactions(transactionFile);
            runApp();
        } catch (IOException e) {
            System.out.println("An unexpected IOException occurred");
        } catch (Exception e){
            System.out.println("An unexpected error occurred");
        }
    }

    private static void runApp() throws IOException{
        // Method that starts the interactive part of the application. Handles looping.
        while (true){
            displayHomeScreen();
            String choice = console.promptForString("Please enter your choice (D, P, L, X): ");
            switch (choice){
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
                    System.out.println("Exiting application.");
                    transactionList.saveTransactions(transactionFile);
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void displayHomeScreen(){
        // Displays the options the user has when first starting the app
        System.out.println("\nHome screen:" +
                "\nD) Add Deposit" +
                "\nP) Make Payment" +
                "\nL) Ledger" +
                "\nX) Exit");
    }

    private static void addTransaction(boolean isDeposit) throws IOException{
        // Methodology for adding a transaction (deposit or payment)
        // Uses a boolean (isDeposit) to ascertain how the deposit should be processed
    }

    private static void displayLedger() throws IOException{
        // Displays ledger screen and it's possible options
    }
}