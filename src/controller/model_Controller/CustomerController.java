package controller.model_Controller;

import db.DbConnection;
import model.Customer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerController {
    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Customer VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, c.getCusID());
        stm.setObject(2, c.getCusName());
        stm.setObject(3, c.getCusNIC());
        stm.setObject(4, c.getCusAddress());
        stm.setObject(5, c.getContact());
        return stm.executeUpdate() > 0;
    }

    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE cusID LIKE '%"+id+"%' || cusNIC LIKE '%"+id+"%'|| cusName LIKE '%"+id+"%'");

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        } else {
            return null;
        }
    }

    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
        ArrayList<Customer> cusList = new ArrayList<>();
        while (rst.next()) {
            cusList.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return cusList;
    }


    public String getCustomerID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT cusID FROM Customer ORDER BY cusID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("C")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "C00" + tempID;
            } else if (tempID <= 99) {
                return "C0" + tempID;
            } else {
                return "C" + tempID;
            }
        } else {
            return "C001";
        }
    }

    public int getCusCount() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Customer";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    public boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Customer SET cusName=?, cusNIC=?, cusAddress=?, contact=? WHERE cusID=?");
        stm.setObject(1, customer.getCusName());
        stm.setObject(2, customer.getCusNIC());
        stm.setObject(3, customer.getCusAddress());
        stm.setObject(4, customer.getContact());
        stm.setObject(5, customer.getCusID());
        return stm.executeUpdate() > 0;
    }

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Customer WHERE cusID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Customer> getCustomers(String text) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE cusID LIKE '%"+text+"%' || cusNIC LIKE '%"+text+"%'|| cusName LIKE '%"+text+"%'").executeQuery();
        ArrayList<Customer> cusList = new ArrayList<>();
        while (rst.next()) {
            cusList.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return cusList;
    }
}
