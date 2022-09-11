package model;

public class Payment {
    private String invoiceNo;
    private String orderID;
    private String paymentMethod;
    private double totalAmount;

    public Payment() {
    }

    public Payment(String invoiceNo, String orderID, String paymentMethod, double totalAmount) {
        this.invoiceNo = invoiceNo;
        this.orderID = orderID;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
