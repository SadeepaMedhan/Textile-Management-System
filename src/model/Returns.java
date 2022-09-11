package model;

import java.util.ArrayList;

public class Returns {
    private String receiptNo;
    private String invoiceNo;
    private String date;
    private double totalPrice;
    private ArrayList<ReturnDetail> returnDetails;

    public Returns() {
    }

    public Returns(String receiptNo, String invoiceNo, String date, double totalPrice, ArrayList<ReturnDetail> returnDetails) {
        this.receiptNo = receiptNo;
        this.invoiceNo = invoiceNo;
        this.date = date;
        this.totalPrice = totalPrice;
        this.returnDetails = returnDetails;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<ReturnDetail> getReturnDetails() {
        return returnDetails;
    }

    public void setReturnDetails(ArrayList<ReturnDetail> returnDetails) {
        this.returnDetails = returnDetails;
    }
}
