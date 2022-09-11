package model;

public class ProductInfoForBill {
    private String productID;
    private String detail;
    private double price;
    private int qty;
    private double amount;

    public ProductInfoForBill() {
    }

    public ProductInfoForBill(String productID, String detail, double price, int qty, double amount) {
        this.productID = productID;
        this.detail = detail;
        this.price = price;
        this.qty = qty;
        this.amount = amount;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
