package model;

public class DailySales {
    private String productID;
    private int qty;
    private double discount;
    private double totPrice;
    private String orderID;

    public DailySales() {
    }

    public DailySales(String productID, int qty, double discount, double totPrice, String orderID) {
        this.productID = productID;
        this.qty = qty;
        this.discount = discount;
        this.totPrice = totPrice;
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
