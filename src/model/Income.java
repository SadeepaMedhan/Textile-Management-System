package model;

public class Income {
    private String recordNo;
    private String date;
    private String invoiceNo;
    private String salaryID;
    private String description;
    private double balance;

    public Income() {
    }

    public Income(String recordNo, String date, String invoiceNo, String salaryID, String description, double balance) {
        this.recordNo = recordNo;
        this.date = date;
        this.invoiceNo = invoiceNo;
        this.salaryID = salaryID;
        this.description = description;
        this.balance = balance;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getSalaryID() {
        return salaryID;
    }

    public void setSalaryID(String salaryID) {
        this.salaryID = salaryID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
