package com.pluralsight;

import java.util.List;

public class DisplayUtils {

    private static final int dateTimeWidth = 21;
    private static final int descWidth = 40;
    private static final int vendorWidth = 30;
    private static final int amountWidth = 15;

    // Creates the format pattern string "%-21s %-40s %-30s %15s" used later to format table columns
    // This line inserts the integer column widths (e.g., dateTimeWidth) using %d
    // Adds the literal % symbols needed using the %% escape sequence
    private static final String headerFormat = String.format("%%-%ds %%-%ds %%-%ds %%%ds",
            dateTimeWidth, descWidth, vendorWidth, amountWidth);

    // Creates the separator line string
    // Uses the headerFormat pattern above to ensure the dashes align with the columns
    private static final String separatorLine = String.format(headerFormat,
            "-".repeat(dateTimeWidth), "-".repeat(descWidth), "-".repeat(vendorWidth), "-".repeat(amountWidth));

    // Prints the formatted column header row and the separator line beneath it
    // Uses printf with the headerFormat with %n for newline and column titles for alignment.
    public static void printHeader() {
        System.out.printf((headerFormat) + "%n", "Date/Time", "Description", "Vendor", "Amount");
        System.out.println(separatorLine);

    }

    // Prints a formatted list of transactions, usable with specific reports or full reports
    // Displays the provided title, handles null or empty lists
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
