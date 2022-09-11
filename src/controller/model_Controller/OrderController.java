package controller.model_Controller;

import db.DbConnection;
import model.DailySales;
import model.Order;
import model.OrderDetail;
import model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderController {

    public String getOrderID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT orderID FROM `Order` ORDER BY orderID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "O-00" + tempID;
            } else if (tempID <= 99) {
                return "O-0" + tempID;
            } else {
                return "O-" + tempID;
            }

        } else {
            return "O-001";
        }
    }

    public boolean placeOrder(Order order, Payment payment) {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stm = con.prepareStatement("INSERT INTO `Order` VALUES(?,?,?,?,?,?,?)");
            stm.setObject(1, order.getOrderID());
            stm.setObject(2, order.getOrderDate());
            stm.setObject(3, order.getOrderTime());
            stm.setObject(4, order.getCusID());
            stm.setObject(5, order.getCashierID());
            stm.setObject(6, order.getStatus());
            stm.setObject(7, order.getTotalPrice());

            if (stm.executeUpdate() > 0) {
                if (saveOrderDetail(order.getOrderID(), order.getProducts())) {
                    if (new PaymentController().setPayment(payment, order.getOrderDate())) {
                        con.commit();
                        return true;
                    }
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    private boolean saveOrderDetail(String orderID, ArrayList<OrderDetail> products) throws SQLException, ClassNotFoundException {
        for (OrderDetail temp : products) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO OrderDetail VALUES(?,?,?,?,?)");
            stm.setObject(1, orderID);
            stm.setObject(2, temp.getProductID());
            stm.setObject(3, temp.getProductQTY());
            stm.setObject(4, temp.getDiscount());
            stm.setObject(5, temp.getTotalPrice());

            if (stm.executeUpdate() > 0) {
                if (updateQty(temp.getProductID(), temp.getProductQTY())) {
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String productID, int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Product SET qtyOnHand=(qtyOnHand-" + qty + ") WHERE productID='" + productID + "'");
        return stm.executeUpdate() > 0;
    }

    public ArrayList<Order> getAllOrders() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order`").executeQuery();
        ArrayList<Order> orderList = new ArrayList<>();
        while (rst.next()) {
            orderList.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDouble(7),
                    getOrdersDetails(rst.getString(1))
            ));
        }
        return orderList;
    }

    public ArrayList<OrderDetail> getOrdersDetails(String orderID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM OrderDetail WHERE orderID=?");
        stm.setObject(1, orderID);
        ResultSet rst = stm.executeQuery();
        ArrayList<OrderDetail> productList = new ArrayList<>();
        while (rst.next()) {
            productList.add(new OrderDetail(
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }
        return productList;
    }

    public Order getOrder(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE orderID='" + orderID + "'").executeQuery();

        if (rst.next()) {
            return new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDouble(7),
                    getOrdersDetails(rst.getString(1))
            );
        }
        return null;
    }

    public ArrayList<Order> searchOrders(String text) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM `Order` WHERE orderDate LIKE '%" + text + "%' || orderID LIKE '%" + text + "%'";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        ArrayList<Order> orderList = new ArrayList<>();
        while (rst.next()) {
            orderList.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDouble(7),
                    getOrdersDetails(rst.getString(1))
            ));
        }
        return orderList;
    }

    public int getAllProductQTY(String orderID) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM OrderDetail WHERE orderID= '" + orderID + "'";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    public int getOrdersCount() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM `Order`";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    public ArrayList<DailySales> getAllSales() throws SQLException, ClassNotFoundException {
        String query = "select productID, SUM(productQty), discount, SUM(totalPrice), orderID From orderDetail Group By productID ORDER By SUM(productQty) DESC ";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        ArrayList<DailySales> saleList = new ArrayList<>();
        while (rst.next()) {
            saleList.add(new DailySales(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getString(5)
            ));
        }
        return saleList;
    }

    public ArrayList<String> getOrderIdsFromDate(String date) throws SQLException, ClassNotFoundException {
        String query = " select * from `order` where orderDate='" + date + "'";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        ArrayList<String> idList = new ArrayList<>();
        while (rst.next()) {
            idList.add(rst.getString(1));
        }
        return idList;
    }

    public ArrayList<OrderDetail> getOrdersDetailProductID(String proID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM OrderDetail WHERE productID LIKE '%" + proID + "%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<OrderDetail> productList = new ArrayList<>();
        while (rst.next()) {
            productList.add(new OrderDetail(
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }
        return productList;
    }

    public double getPriceCustomerWise(String cusID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT totalPrice FROM `order` WHERE cusID='" +cusID+"'");
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getDouble(1);
        }
        return 0;
    }
}
