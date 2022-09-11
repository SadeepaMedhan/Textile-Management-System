package view.tm;


import javafx.scene.control.Button;

public class OrdersTM {
    private String orderID ;
    private String orderDate;
    private String cusName;
    private String cashierID;
    private int productsQty ;
    private String status;
    private Button viewProducts ;
    private double totalPrice ;

    public OrdersTM() {
    }

    public OrdersTM(String orderID, String orderDate, String cusName, String cashierID, int productsQty, String status, Button viewProducts, double totalPrice) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cusName = cusName;
        this.cashierID = cashierID;
        this.productsQty = productsQty;
        this.status = status;
        this.viewProducts = viewProducts;
        this.totalPrice = totalPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCashierID() {
        return cashierID;
    }

    public void setCashierID(String cashierID) {
        this.cashierID = cashierID;
    }

    public int getProductsQty() {
        return productsQty;
    }

    public void setProductsQty(int productsQty) {
        this.productsQty = productsQty;
    }

    public Button getViewProducts() {
        return viewProducts;
    }

    public void setViewProducts(Button viewProducts) {
        this.viewProducts = viewProducts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
