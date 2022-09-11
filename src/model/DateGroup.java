package model;

public class DateGroup {
    private String day;
    private double balance;

    public DateGroup() {
    }

    public DateGroup(String day, double balance) {
        this.day = day;
        this.balance = balance;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
