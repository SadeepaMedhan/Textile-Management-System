package view.tm;

public class DailyIncomeTM {
    private String date;
    private int totalSale;
    private double income;
    private double expense;
    private String description;
    private double grandTotal;

    public DailyIncomeTM() {
    }

    public DailyIncomeTM(String date, int totalSale, double income, double expense, String description, double grandTotal) {
        this.date = date;
        this.totalSale = totalSale;
        this.income = income;
        this.expense = expense;
        this.description = description;
        this.grandTotal = grandTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(int totalSale) {
        this.totalSale = totalSale;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
