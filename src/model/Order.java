package model;

import java.util.ArrayList;

public class Order {
    private String orderID ;
    private String orderDate;
    private String orderTime;
    private String cusID;
    private String cashierID;
    private String status ;
    private double totalPrice ;
    private ArrayList<OrderDetail> products;

    public Order() {
    }

    public Order(String orderID, String orderDate, String orderTime, String cusID, String cashierID, String status, double totalPrice, ArrayList<OrderDetail> products) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cusID = cusID;
        this.cashierID = cashierID;
        this.status = status;
        this.totalPrice = totalPrice;
        this.products = products;
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

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCashierID() {
        return cashierID;
    }

    public void setCashierID(String cashierID) {
        this.cashierID = cashierID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<OrderDetail> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<OrderDetail> products) {
        this.products = products;
    }
}
