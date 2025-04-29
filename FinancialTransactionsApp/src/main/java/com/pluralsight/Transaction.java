package com.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Represents a singular financial transaction (payment/deposit)
public class Transaction {
    private LocalDateTime dateTime;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDateTime dateTime, String description, String vendor, double amount) {
        this.dateTime = dateTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    // Override toString to get a formatted string for the transaction
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s|%s|%s|%.2f", dateTime.format(formatter), description, vendor, amount);
    }

    public String toDisplayString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        int dateTimeWidth = 21;
        int descWidth = 40;
        int vendorWidth = 30;
        int amountWidth = 15;

        String displayDesc = description;
        if (displayDesc == null) {
            displayDesc = "";
        }
        if (displayDesc.length() > descWidth) {
            displayDesc = displayDesc.substring(0, descWidth);

        }

        String displayVendor = vendor;
        if (displayVendor == null) {
            displayVendor = "";
        }
        if (displayVendor.length() > vendorWidth){
            displayVendor = displayVendor.substring(0, vendorWidth);
        }

        String formattedString = String.format("%%-%ds %%-%ds %%-%ds %%%d.2f",
                dateTimeWidth, descWidth, vendorWidth, amountWidth);

        return String.format(formattedString, formattedDateTime, displayDesc, displayVendor, amount);
    }

    // Method to get a transaction object from a string
    public static Transaction parsedTransaction(String transactionString) {
        String[] parts = transactionString.split("\\|"); // Splits at | delimiter
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid transaction string: " + transactionString);
        }
        try {
            // Parses inputs from a delimited string array into proper transaction format and assigns the variables accordingly
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(parts[0], formatter);
            String description = parts[1];
            String vendor = parts[2];
            double amount = Double.parseDouble(parts[3]);
            return new Transaction(dateTime, description, vendor, amount);
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing transaction string: " + transactionString);
        }
    }
}
