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

        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public String getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDate getDate() {
        return date;
    }
    
    @Override
    public String toString() {
        return "[" + date + "] " + category + " - " + description + ": ₹" + amount;
    }

}



