import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class TransactionsCalculations {

    public static List<Transaction> getTransactionsOfAParticularDay(String fileLocation, String date) {
        Path path = Paths.get(fileLocation);

        String transactionList= readFileToString(path);
        Transaction[] transactions = convertStringToArrayOfTransactions(transactionList);

        LocalDate dateOfTransaction = parseDateToLocalDateObject(date);

        return Arrays.stream(transactions).
                filter(transaction -> (transaction.getDate().equals(dateOfTransaction)))
                .toList();
    }

    private static LocalDate parseDateToLocalDateObject(String date) {
        LocalDate dateOfTransaction;
        try {
            dateOfTransaction = LocalDate.parse(date);
        }
        catch (DateTimeParseException error) {
            throw new IllegalArgumentException("Invalid date. Please try again");
        }
        return dateOfTransaction;
    }

    private static Transaction[] convertStringToArrayOfTransactions(String transactionList) {
        Transaction[] transactions;
        try {
            transactions = JsonSerializer.deserialize(transactionList);
        }
        catch (IOException error) {
            throw new IllegalArgumentException("Invalid format JSON.");
        }
        return transactions;
    }

    private static String readFileToString(Path path) {
        String transactionList;
        try {
            transactionList = Files.readString(path);
        }
        catch (IOException error) {
            throw new IllegalArgumentException("File location does not exist.");
        }
        return transactionList;
    }


    public static List<Transaction> getTransactionsWithinPeriod(String fileLocation, String startDate, String endDate) {
        Path path = Paths.get(fileLocation);

        String transactionList= readFileToString(path);
        Transaction[] transactions = convertStringToArrayOfTransactions(transactionList);

        LocalDate startPeriodDate = parseDateToLocalDateObject(startDate);
        LocalDate endPeriodDate = parseDateToLocalDateObject(endDate);

        return Arrays.stream(transactions).
                filter(transaction -> (
                        isTransactionWithDatePeriod(transaction.getDate(), startPeriodDate, endPeriodDate)))
                .toList();
    }

    public static boolean isTransactionWithDatePeriod(LocalDate transactionDate, LocalDate startDate, LocalDate endDate) {
        return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
    }


    public static double getAverageTransactionAmountWithinPeriod(String fileLocation, String startDate, String endDate) {
        Path path = Paths.get(fileLocation);

        String transactionList= readFileToString(path);
        Transaction[] transactions = convertStringToArrayOfTransactions(transactionList);

        LocalDate startPeriodDate = parseDateToLocalDateObject(startDate);
        LocalDate endPeriodDate = parseDateToLocalDateObject(endDate);

        return Arrays.stream(transactions)
                .filter(transaction -> (
                        isTransactionWithDatePeriod(transaction.getDate(), startPeriodDate, endPeriodDate)))
                .mapToDouble(transaction -> (transaction.getAmount())).average().orElseThrow();
    }

    public static AccountSummary getAccountSummary(String fileLocation, int accountId) {
        Path path = Paths.get(fileLocation);

        String transactionList= readFileToString(path);
        Transaction[] transactions = convertStringToArrayOfTransactions(transactionList);

        int transactionCount = (int) Arrays.stream(transactions)
                .filter(transaction -> transaction.getId() == accountId)
                .count();

        String name = Arrays.stream(transactions)
                .filter(transaction -> transaction.getId() == accountId)
                .toList().getFirst().getName();

        double accountBalance = Arrays.stream(transactions)
                .filter(transaction -> transaction.getId() == accountId)
                        .mapToDouble(transaction -> (transaction.getAmount()))
                .reduce(0, (balance, transactionAmount)-> balance + transactionAmount);

        return new AccountSummary(name, accountBalance, transactionCount);
    }

}
