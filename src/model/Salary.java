package model;

public class Salary {
    private String salaryID;
    private String date;
    private double salaryAmount;

    public Salary() {
    }

    public Salary(String salaryID, String date, double salaryAmount) {
        this.salaryID = salaryID;
        this.date = date;
        this.salaryAmount = salaryAmount;
    }

    public String getSalaryID() {
        return salaryID;
    }

    public void setSalaryID(String salaryID) {
        this.salaryID = salaryID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(double salaryAmount) {
        this.salaryAmount = salaryAmount;
    }
}
