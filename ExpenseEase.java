import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

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

class ExpenseTracker {
    private Map<String, List<Expense>> expensesByCategory;

    public ExpenseTracker() {
        expensesByCategory = new HashMap<>();
    }

    public void addExpense(String category, String description, double amount) {
        Expense expense = new Expense(category, description, amount);
        
        expensesByCategory.putIfAbsent(category, new ArrayList<>());
        expensesByCategory.get(category).add(expense);


        System.out.println("Expense added successfully.");
    }

    public void viewExpensesByCategory(String category) {
        List<Expense> expenses = expensesByCategory.getOrDefault(category, new ArrayList<>());

        if (expenses.isEmpty()) {
            System.out.println("No expenses found in category " + category);
        } else {
            System.out.println("Expenses in category " + category + ":");
            for (Expense expense : expenses) {
                System.out.println("Description: " + expense.getDescription() + ", Amount: ₹");
            }
        }
    }

    public void viewSpendingSummary() {
        System.out.println("Spending Summary: ");
        expensesByCategory.forEach((category, expenses) -> {
            double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();
            System.out.println("Category: " + category + ", Total Amount: ₹" + totalAmount);
        });
    }

    public void exportExpensesToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Category,Description,Amount,Date\n"); 
            for (Map.Entry<String, List<Expense>> entry : expensesByCategory.entrySet()) {
                for (Expense expense : entry.getValue()) {
                    writer.write(entry.getKey() + "," + expense.getDescription() + "," + expense.getAmount() + "," + expense.getDate() + "\n");
                }
            }
            System.out.println("Expenses exported successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }


}

public class ExpenseEase {
    public static void main(String[] args) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n==== Expense Tracker ====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses by Category");
            System.out.println("3. View Spending Summary");
            System.out.println("4. Export Expenses to File");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter amount: ");

                    double amount;
                    try {
                        amount = Double.parseDouble(scanner.nextLine());
                        if (amount <= 0) throw new IllegalArgumentException();
                    } catch (Exception e) {
                        System.out.println("Invalid amount! Please enter a positive number.");
                        continue;
                    }

                    expenseTracker.addExpense(category, description, amount);
                    break;

                case 2:
                    System.out.print("Enter category: ");
                    category = scanner.nextLine();
                    expenseTracker.viewExpensesByCategory(category);
                    break;

                case 3:
                    expenseTracker.viewSpendingSummary();
                    break;

                case 4:
                    expenseTracker.exportExpensesToFile("expenses.csv"); 
                    break;

                case 5:
                    exit = true;
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }

        scanner.close();
    }
}


