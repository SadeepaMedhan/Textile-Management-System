package controller.model_Controller;

import db.DbConnection;
import model.Income;
import model.ReturnDetail;
import model.Returns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnController {
    public String getReceiptNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT returnNo FROM ReturnOrder ORDER BY returnNo DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("R")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "R00" + tempID;
            } else if (tempID <= 99) {
                return "R0" + tempID;
            } else {
                return "R" + tempID;
            }
        } else {
            return "R001";
        }
    }

    public boolean setReturn(Returns r) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO ReturnOrder VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, r.getReceiptNo());
        stm.setObject(2, r.getInvoiceNo());
        stm.setObject(3, r.getDate());
        stm.setObject(4, r.getTotalPrice());
        if (stm.executeUpdate() > 0) {
            if (setReturnDetail(r.getReturnDetails())) {
                return true;
            }
        }
        return false;
    }

    private boolean setReturnDetail(ArrayList<ReturnDetail> returnDetails) throws SQLException, ClassNotFoundException {
        for (ReturnDetail rd : returnDetails) {
            Connection con = DbConnection.getInstance().getConnection();
            String query = "INSERT INTO ReturnDetail VALUES(?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setObject(1, rd.getReceiptNo());
            stm.setObject(2, rd.getProductNo());
            stm.setObject(3, rd.getQty());
            stm.setObject(4, rd.getDescription());
            if (stm.executeUpdate() > 0) {
            } else {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Returns> getAllReturns() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM ReturnOrder").executeQuery();
        ArrayList<Returns> returnList = new ArrayList<>();
        while (rst.next()) {
            returnList.add(new Returns(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getDouble(4),
                            getReturnDetail(rst.getString(1))
                    )
            );
        }
        return returnList;
    }

    private ArrayList<ReturnDetail> getReturnDetail(String rID) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM returndetail WHERE returnNo='" + rID + "'").executeQuery();
        ArrayList<ReturnDetail> returnDetailList = new ArrayList<>();
        while (rst.next()) {
            returnDetailList.add(new ReturnDetail(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getInt(3),
                            rst.getString(4)
                    )
            );
        }
        return returnDetailList;

    }

    public Returns getReturn(String receipt) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM ReturnOrder WHERE returnNo='" + receipt + "'").executeQuery();
        if (rst.next()) {
            return new Returns(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    getReturnDetail(rst.getString(1))
            );
        }
        return null;
    }

    public ArrayList<ReturnDetail> getAllReturnDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM returndetail").executeQuery();
        ArrayList<ReturnDetail> returnDetailList = new ArrayList<>();
        while (rst.next()) {
            returnDetailList.add(new ReturnDetail(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getInt(3),
                            rst.getString(4)
                    )
            );
        }
        return returnDetailList;
    }

    public ReturnDetail getReturnProduct(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM ReturnDetail WHERE productID='" + id + "'").executeQuery();
        if (rst.next()) {
            return new ReturnDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    public int getCount(String desc) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM ReturnDetail WHERE description='" + desc + "'").executeQuery();
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    public ArrayList<ReturnDetail> getProductList(String txt) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM returndetail WHERE description='" + txt + "'").executeQuery();
        ArrayList<ReturnDetail> returnDetailList = new ArrayList<>();
        while (rst.next()) {
            returnDetailList.add(new ReturnDetail(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getInt(3),
                            rst.getString(4)
                    )
            );
        }
        return returnDetailList;
    }

    public boolean setWasteAllProducts() throws SQLException, ClassNotFoundException {
        ArrayList<ReturnDetail> damage = getProductList("Damage");
        double wastePrice = 0;
        for (ReturnDetail r : damage) {
            Returns aReturn = getReturn(r.getReceiptNo());
            wastePrice += aReturn.getTotalPrice();
            if (new IncomeController().setIncome(new Income("", aReturn.getDate(), "-", aReturn.getReceiptNo(), "Waste", -wastePrice))) {
                removeReturnDetail(r.getReceiptNo());
            } else {
                return false;
            }
        }
        return true;
    }

    private void removeReturnDetail(String receiptNo) throws SQLException, ClassNotFoundException {
            if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM returndetail WHERE returnNo='" +receiptNo+ "'").executeUpdate() > 0){
        }
    }

    public double isExit(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Returns> allReturns = getAllReturns();
        for (Returns r : allReturns) {
            if (r.getReceiptNo().equals(id)) {
                return r.getTotalPrice();
            }
        }
        return 0;
    }

    public boolean addToStockAllProducts() throws SQLException, ClassNotFoundException {
        ArrayList<ReturnDetail> sizeVaries = new ReturnController().getProductList("Size varies");
        for (ReturnDetail r:sizeVaries) {
            if(new ProductController().addStockFromReturn(r.getProductNo(),r.getQty())){
                removeReturnDetail(r.getReceiptNo());
            }
            else {
                return false;
            }
        }
        return true;
    }
}
