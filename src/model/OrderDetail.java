package model;

public class OrderDetail {
    private String productID;
    private int productQTY;
    private double discount;
    private double totalPrice;

    public OrderDetail() {
    }

    public OrderDetail(String productID, int productQTY, double discount, double totalPrice) {
        this.productID = productID;
        this.productQTY = productQTY;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getProductQTY() {
        return productQTY;
    }

    public void setProductQTY(int productQTY) {
        this.productQTY = productQTY;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
