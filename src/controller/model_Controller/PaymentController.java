package controller.model_Controller;

import db.DbConnection;
import model.Income;
import model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentController {

    public boolean setPayment(Payment p, String orderDate) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Payment VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, p.getInvoiceNo());
        stm.setObject(2, p.getOrderID());
        stm.setObject(3, p.getPaymentMethod());
        stm.setObject(4, p.getTotalAmount());

        if(stm.executeUpdate() > 0){
            return new IncomeController().setIncome(new Income(
                    "",
                    orderDate,
                    p.getInvoiceNo(),
                    "-",
                    "sale",
                    p.getTotalAmount()
            ));
        }
        return false;
    }

    public String getInvoiceID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT invoiceNo FROM Payment ORDER BY invoiceNo DESC LIMIT 1").executeQuery();
        if(rst.next()){
            int tempID = Integer.parseInt(rst.getString(1));
            tempID=tempID+1;
            if(tempID<=9){
                return "00000"+tempID;
            }else if(tempID<=99){
                return "0000"+tempID;
            }else if(tempID<=999){
                return "000"+tempID;
            }else if(tempID<=9999){
                return "00"+tempID;
            }else if(tempID<=99999){
                return "0"+tempID;
            }else{
                return ""+tempID;
            }
        }else{
            return "000001";
        }
    }

    public Payment getPayment(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment WHERE invoiceNo='"+id+"'").executeQuery();

        if(rst.next()) {
            return new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }

    public String getInvoiceNo(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment WHERE orderID='"+orderID+"'").executeQuery();
        if(rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
