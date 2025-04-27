package com.pluralsight;

import java.time.format.DateTimeFormatter;

public class Main {

    // Creating variables that should remain the same throughout the entirety of the programs runtime.
    private static final DateTimeFormatter DtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String transactionFile = "transactions.csv";

    public static void main(String[] args) {

    }
}