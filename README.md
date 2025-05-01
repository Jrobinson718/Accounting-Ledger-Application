# Accounting-Ledger-Application
YearUp Capstone 1
# Accounting Ledger - Command Line Application

## Overview

This project is a command-line Java application designed to help users track their financial transactions (deposits and payments). It allows users to add new transactions, view their ledger, generate various financial reports, and search through their transaction history. The application saves data to a CSV file (`transactions.csv`) for persistence between runs.

## Features

* **Add Transactions:** Add deposits (positive amounts) or payments (negative amounts) with date, time, description, vendor, and amount.
* **Persistent Storage:** Transactions are loaded from and saved to `transactions.csv` upon startup and exit.
* **Ledger Views:**
    * View all transactions (newest first).
    * View only deposits.
    * View only payments.
* **Formatted Output:** Transaction lists are displayed in an aligned table format using box-drawing characters in the console. Amounts are colored (green for deposits, red for payments).
* **Reporting:** Generate reports for specific time periods:
    * Month To Date
    * Previous Month
    * Year To Date
    * Previous Year
* **Search Functionality:**
    * Search transactions by vendor name (case-insensitive).
    * Custom search by optional criteria: start date, end date, description, vendor.

## Project Structure

The application is organized into seven classes, each with a responsibility:

* **`FinancialTransactionsApp`:** The main application class containing the `main` method, user interface loops (menus), and methods controlling the application flow.
* **`Transaction`:** Represents a single financial transaction with the properties date/time, description, vendor, and amount. Includes methods for saving (`toString`) and parsing (`parsedTransaction`) its data for the CSV file.
* **`TransactionList`:** Manages `ArrayList` of `Transaction` objects. Handles loading/saving the list from/to the CSV file and provides methods for filtering and searching transactions.
* **`Console`:** A utility class for handling user input from the command line, including prompts for strings, floats, etc, with validation.
* **`DisplayUtils`:** A utility class responsible for formatting and printing the transaction lists to the console in an aligned table format with colors and box-drawing characters.
* **`DateUtils`:** A utility class providing helper methods to calculate specific date ranges (Month-to-Date, Previous Month) used in reporting.
* **`ColorCodes`:** Uses ANSI escape code to color text (used by `DisplayUtils`).

## Screenshots

### Class Structure (Code Snippets)

![Screenshot 2025-05-01 114747](https://github.com/user-attachments/assets/f55425ec-04ac-4995-9c81-4d9eabf21331)

![Screenshot 2025-05-01 114850](https://github.com/user-attachments/assets/1de9c538-cd8e-446e-9d0a-e7e9ade5f6ef)

![Screenshot 2025-05-01 115046](https://github.com/user-attachments/assets/5981f402-4481-451f-a078-f55f444c7ef1)

![Screenshot 2025-05-01 115155](https://github.com/user-attachments/assets/a83bf792-3803-445f-8398-58efd02e4d32)

![Screenshot 2025-05-01 115220](https://github.com/user-attachments/assets/19d246de-db3f-42e0-99f3-887149e79171)

### Running Application

![Screenshot 2025-05-01 115325](https://github.com/user-attachments/assets/40afbabc-f24c-45d8-97c8-610249cc7423)

![Screenshot 2025-05-01 115352](https://github.com/user-attachments/assets/e49b0722-45dd-4adc-8026-26a25968f78d)

![Screenshot 2025-05-01 115421](https://github.com/user-attachments/assets/89583455-14b8-488c-b804-94f112d26e85)

![Screenshot 2025-05-01 115510](https://github.com/user-attachments/assets/5e3ad403-f6f4-43a0-b93b-c0ef9b244ceb)

![Screenshot 2025-05-01 115630](https://github.com/user-attachments/assets/54adcd6b-6284-4808-84dd-238844d5743e)

## Future Improvements

* Add filtering/searching by amount range to Custom Search.
* Implement editing or deleting existing transactions.
* Implement sorting options for display (sort by amount, vendor).
