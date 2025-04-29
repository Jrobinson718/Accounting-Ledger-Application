package com.pluralsight;

import java.util.List;

public class DisplayUtils {

    private static final int dateTimeWidth = 21;
    private static final int descWidth = 40;
    private static final int vendorWidth = 30;
    private static final int amountWidth = 15;

    private static final String headerFormat = String.format("%%-%ds %%-%ds %%-%ds %%%ds",
            dateTimeWidth, descWidth, vendorWidth, amountWidth);

    private static final String separatorLine = String.format(headerFormat,
            "-".repeat(dateTimeWidth), "-".repeat(descWidth), "-".repeat(vendorWidth), "-".repeat(amountWidth));

    public static void printHeader() {
        System.out.printf((headerFormat) + "%n", "Date/Time", "Description", "Vendor", "Amount");
        System.out.println(separatorLine);

    }

    public static void printFormattedList(List<Transaction> transactions, String title) {
        System.out.println("\n" + title + ":");

        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }

        printHeader();

        for (int i = transactions.size() - 1; i >= 0; i--){
            System.out.println(transactions.get(i).toDisplayString());
        }
    }
}
