package com.pluralsight;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class DisplayUtils {
    //   === Column width initialization ===
    private static final int dateTimeWidth = 21;
    private static final int descWidth = 40;
    private static final int vendorWidth = 30;
    private static final int amountWidth = 15;

    //   === Box drawing characters ===
    private static final char horizontal = '─';
    private static final char vertical = '│';
    private static final char topLeft = '┌';
    private static final char topRight = '┐';
    private static final char bottomLeft = '└';
    private static final char bottomRight = '┘';
    private static final char crossTop = '┬';
    private static final char crossBottom = '┴';
    private static final char crossLeft = '├';
    private static final char crossRight = '┤';
    private static final char crossMiddle = '┼';

    //   === Format strings for data rows ===
    // Uses %s for the amount column since we not pre-format with "$"

    private static final String dataRowFormat =
            vertical + "%-" + dateTimeWidth + "s" +
            vertical + "%-" + descWidth + "s" +
            vertical + "%-" + vendorWidth + "s" +
            vertical + "%" + amountWidth + "s" +
            vertical + "%n";

    // Pattern used to remove escape codes for length calculation
    private static final Pattern escapePattern = Pattern.compile("\u001B\\[[;\\d]*m");
    //   === Border and separator strings ===
    private static final String topBorder = buildBorder(topLeft, topRight, crossTop);
    private static final String headerSeparator = buildBorder(crossLeft, crossRight, crossMiddle);
    private static final String bottomBorder = buildBorder(bottomLeft, bottomRight, crossBottom);

    //   === Helper method to build border/separator lines ===
    private static String buildBorder(char left, char right, char cross) {
        StringBuilder sb = new StringBuilder();
        sb.append(left);
        sb.append(String.valueOf(horizontal).repeat(dateTimeWidth));
        sb.append(cross);
        sb.append(String.valueOf(horizontal).repeat(descWidth));
        sb.append(cross);
        sb.append(String.valueOf(horizontal).repeat(vendorWidth));
        sb.append(cross);
        sb.append(String.valueOf(horizontal).repeat(amountWidth));
        sb.append(right);
        return sb.toString();
    }

    //   === Helper method to center text within the width ===
    private static String centerText(String text, int width) {
        if (text == null){
            text = "";
        }

        int visibleLength = escapePattern.matcher(text).replaceAll("").length();
        if (visibleLength >= width) {
            return text.substring(0, width);
        }

        int padding = width - visibleLength;
        int padLeft = padding / 2;
        int padRight = padding - padLeft;
        return " ".repeat(padLeft) + text + " ".repeat(padRight);
    }

    //   === Main printing method ===
    // Prints a formatted list of transactions within a box table.
    //Displays the provided title and handle null or empty lists
    public static void printFormattedList(List<Transaction> transactions, String title) {
        System.out.println();
        if (title != null && !title.isBlank()){
            String titleSeparator = "=".repeat(title.length() + 4);
            System.out.println(titleSeparator);
            System.out.println("= " + title + " =");
            System.out.println(titleSeparator);
        }

        // Handles empty lists
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions to display." + "\n");
            return;
        }

        // Print table header
        System.out.println(topBorder);
        //Prints centered header title within vertical bars
        System.out.printf("%c%s%c%s%c%s%c%s%c%n",
                vertical, centerText("Date/Time", dateTimeWidth),
                vertical, centerText("Description", descWidth),
                vertical, centerText("Vendor", vendorWidth),
                vertical, centerText("Amount", amountWidth),
                vertical);
        System.out.println(headerSeparator);

        DateTimeFormatter DtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);

            String formattedDateTime = t.getDateTime().format(DtFormatter);
            if (formattedDateTime.length() > dateTimeWidth) {
                formattedDateTime = formattedDateTime.substring(0, dateTimeWidth);
            }

            String displayDesc = t.getDescription();
            if (displayDesc == null){
                displayDesc = "";
            }
            if (displayDesc.length() > descWidth) {
                displayDesc = displayDesc.substring(0, descWidth -3) + "...";
            }

            String displayVendor = t.getVendor();
            if (displayVendor == null){
                displayVendor = "";
            }
            if (displayVendor.length() > vendorWidth) {
                displayVendor = displayVendor.substring(0, vendorWidth - 3) + "...";
            }

            //   === Formats amount color based on if it is a payment/deposit
            double amount = t.getAmount();
            String amountColor;
            if (amount > 0) {
                amountColor = ColorCodes.GREEN;
            } else if (amount < 0) {
                amountColor = ColorCodes.RED;
            }else {
                amountColor = ColorCodes.RESET;
            }


            // New formatted amount with dollar sign first
            String formattedAmount = String.format("%s$%.2f%s",
                    amountColor, amount, ColorCodes.RESET);

            // Calculate visible length (strips ANSI)
            String visibleAmount = escapePattern.matcher(formattedAmount).replaceAll("");
            int visibleLength = visibleAmount.length();

            // Creates padding based on the amount and visible length
            int paddingNeeded = Math.max(0, amountWidth - visibleLength);
            String padding = " ".repeat(paddingNeeded);
            String finalAmountString = padding + formattedAmount;

            System.out.printf(dataRowFormat, formattedDateTime, displayDesc, displayVendor, finalAmountString);

        }

        System.out.println(bottomBorder + "\n");
    }
}
