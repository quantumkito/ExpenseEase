import java.util.ArrayList;
import java.time.LocalDate;

class Expense {
    private String category;
    private String description;
    private double amount;
    private LocalDate date;

    public Expense (String category, String description, double amount) {
        if (amount <= 0) {
            throw new  IllegalArgumentException("Amount must be positive!");
        }
    }
}



