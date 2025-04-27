package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Class to manage a list of transactions given from the Transaction class
public class TransactionList {
    private List<Transaction> transactions;

    public TransactionList() {
        this.transactions = new ArrayList<>();
    }

    // Adds transaction to list
    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    // Gets all transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Finds transactions within a specified date range
    public List<Transaction> getTransactions(LocalDate startDate, LocalDate endDate) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDateTime().toLocalDate();
            if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                    (transactionDate.isEqual(endDate) || transactionDate.isBefore(endDate))) {
                result.add(transaction);
            }
        }
        return result;
    }

    // Finds transactions for specific vendors
    public List<Transaction> getTransactionsByVendor(String vendor){
        List<Transaction> result = new ArrayList<>();
        for(Transaction transaction : transactions){
            if (transaction.getVendor().equalsIgnoreCase(vendor)){
                result.add(transaction);
            }
        }
        return result;
    }

    // Finds amount of transactions within a date range
    public double getTotalTransactions(LocalDate startDate, LocalDate endDate){
        double total = 0;
        for (Transaction transaction : transactions){
            LocalDate transactionDate = transaction.getDateTime().toLocalDate();
            if((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                    (transactionDate.isEqual(endDate) || transactionDate.isBefore(endDate))){
                total += transaction.getAmount();
            }
        }
        return total;
    }

    // Saving transaction to the transactions.csv file
    public void saveTransactions(String file) throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(Transaction transaction : transactions){
                bw.write(transaction.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions");
        }
    }

    // Load transactions from transactions.csv
    public void loadTransactions(String file) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null){
                try{
                    Transaction transaction = Transaction.parsedTransaction(line);
                    transactions.add(transaction);
                }catch (IllegalArgumentException e){
                    System.out.println("Error loading transactions: ");
                }
            }
        }
    }
}
