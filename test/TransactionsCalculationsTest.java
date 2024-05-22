import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionsCalculationsTest {

    @Test
    public void getTransactionsOfAParticularDayTest() {
        String fileLocation = "C:\\Users\\DELL\\Desktop\\TransactionsAccounts\\src\\transaction.json";
        String date = "2024-05-01";

        List<Transaction> transactionList = TransactionsCalculations.getTransactionsOfAParticularDay(fileLocation, date);

        assertEquals(1, transactionList.size());
        assertEquals("John Doe", transactionList.getFirst().getName());
        assertEquals(5000, transactionList.getFirst().getAmount());
    }

    @Test
    public void getTransactionsOfAParticularDay_FilepathIsInvalidTest() {
        String fileLocation = "invalid file path";
        String date = "2024-05-01";

        assertThrows(IllegalArgumentException.class, ()->TransactionsCalculations.getTransactionsOfAParticularDay(fileLocation, date));
    }

    @Test
    public void getTransactionsOfAParticularDay_DateIsInvalidTest() {
        String fileLocation = "C:\\Users\\DELL\\Desktop\\TransactionsAccounts\\src\\transaction.json";
        String date = "-2024-100-50";

        assertThrows(IllegalArgumentException.class, ()->TransactionsCalculations.getTransactionsOfAParticularDay(fileLocation, date));
    }

    @Test
    public void getTransactionWithinPeriodTest() {
        String fileLocation = "C:\\Users\\DELL\\Desktop\\TransactionsAccounts\\src\\transaction.json";
        String startDate = "2024-05-04";
        String endDate = "2024-05-14";

        List<Transaction> transactionList = TransactionsCalculations.getTransactionsWithinPeriod(fileLocation, startDate, endDate);

        assertEquals(3, transactionList.size());
        assertEquals(-1000.0, transactionList.getFirst().getAmount());
        assertEquals("Jack Smith", transactionList.get(1).getName());
        assertEquals(1, transactionList.get(2).getId());
    }

    @Test
    public void getTransactionWithinPeriod_FileLocationIsInvalidTest() {
        String fileLocation = "invalid file location";
        String startDate = "2024-05-04";
        String endDate = "2024-05-14";

        assertThrows(IllegalArgumentException.class, ()->TransactionsCalculations.getTransactionsWithinPeriod(fileLocation, startDate, endDate));
    }

    @Test
    public void getTransactionWithinPeriod_DateIsInvalidTest() {
        String fileLocation = "C:\\Users\\DELL\\Desktop\\TransactionsAccounts\\src\\transaction.json";
        String startDate = "-2024-500-50";
        String endDate = "2024-05-14";

        assertThrows(IllegalArgumentException.class, ()->TransactionsCalculations.getTransactionsWithinPeriod(fileLocation, startDate, endDate));
    }

    @Test
    public void getAverageTransactionAmountWithinPeriodTest() {
        String fileLocation = "C:\\Users\\DELL\\Desktop\\TransactionsAccounts\\src\\transaction.json";
        String startDate = "2024-05-04";
        String endDate = "2024-05-14";

        double averageTransactionAmountWithinPeriod = TransactionsCalculations.getAverageTransactionAmountWithinPeriod(fileLocation, startDate, endDate);

        assertEquals(5000.00, averageTransactionAmountWithinPeriod);
    }

    @Test
    public void getAverageTransactionAmountWithinPeriod_FileLocationIsInvalidTest() {
        String fileLocation = "invalid file location";
        String startDate = "2024-05-04";
        String endDate = "2024-05-14";

        assertThrows(IllegalArgumentException.class, ()->TransactionsCalculations.getAverageTransactionAmountWithinPeriod(fileLocation, startDate, endDate));
    }

    @Test
    public void getAverageTransactionAmountWithinPeriod_DateIsInvalidTest() {
        String fileLocation = "C:\\Users\\DELL\\Desktop\\TransactionsAccounts\\src\\transaction.json";
        String startDate = "2024-05-04";
        String endDate = "-2024-100-50";

        assertThrows(IllegalArgumentException.class, ()->TransactionsCalculations.getAverageTransactionAmountWithinPeriod(fileLocation, startDate, endDate));
    }

    @Test
    public void getAccountSummaryTest() {
        String fileLocation = "C:\\Users\\DELL\\Desktop\\TransactionsAccounts\\src\\transaction.json";
        int accountId = 1;

        AccountSummary accountSummary = TransactionsCalculations.getAccountSummary(fileLocation, accountId);

        assertEquals("John Doe", accountSummary.getName());
        assertEquals(2, accountSummary.getTransactionCount());
        assertEquals(15000.00, accountSummary.getBalance());
    }

    @Test
    public void getAccountSummary_FileLocationIsInvalidTest() {
        String fileLocation = "invalid file location";
        int accountId = 1;

        assertThrows(IllegalArgumentException.class, ()->TransactionsCalculations.getAccountSummary(fileLocation, accountId));
    }



}