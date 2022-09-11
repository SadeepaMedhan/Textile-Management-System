package controller.model_Controller;

import db.DbConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryController {

    public boolean setSalary(Salary s) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Salary VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, s.getSalaryID());
        stm.setObject(2, s.getDate());
        stm.setObject(3, s.getSalaryAmount());

        if(stm.executeUpdate() > 0){
            return new IncomeController().setIncome(new Income(
                    "",
                    s.getDate(),
                    "-",
                    s.getSalaryID(),
                    "Salary",
                    -s.getSalaryAmount()
            ));
        }
        return false;
    }
    public boolean setSalaryDetail(ArrayList<SalaryDetail> salaryDetails) throws SQLException, ClassNotFoundException {
        for (SalaryDetail sd : salaryDetails) {
            Connection con = DbConnection.getInstance().getConnection();
            String query = "INSERT INTO SalaryDetail VALUES(?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setObject(1, sd.getDate());
            stm.setObject(2, sd.getEmpID());
            stm.setObject(3, sd.getStatus());
            stm.setObject(4, sd.getWorkDays());
            stm.setObject(5, sd.getEpf());
            stm.setObject(6, sd.getBasicSal());
            if (stm.executeUpdate() > 0) {
            } else {
                return false;
            }
        }
        return true;
    }
    public String getSalaryNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT salaryID FROM Salary ORDER BY salaryID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "S-000" + tempID;
            } else if (tempID <= 99) {
                return "S-00" + tempID;
            } else if (tempID <= 999) {
                return "S-0" + tempID;
            } else {
                return "S-" + tempID;
            }

        } else {
            return "S-0001";
        }
    }

    public Salary getSalary(String salaryID) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Salary WHERE salaryID='" + salaryID + "'").executeQuery();
        if (rst.next()) {
            return new Salary(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            );
        }
        return null;
    }

    public ArrayList<Salary> getAllSalary() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Salary").executeQuery();
        ArrayList<Salary> allList = new ArrayList<>();
        while (rst.next()) {
            allList.add( new Salary(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            ));
        }
        return allList;
    }

    public boolean isExit(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Salary WHERE salDate LIKE '%"+date+"%'").executeQuery();
        if (rst.next()) {
            return true;
        }
        return false;
    }

    public ArrayList<SalaryDetail> getSalaryDetail(String month) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM SalaryDetail WHERE salDate LIKE '%"+month+"%' ").executeQuery();
        ArrayList<SalaryDetail> allList = new ArrayList<>();
        while (rst.next()) {
            String empName = "";
            Cashier cashier = new EmployeeController().getCashier(rst.getString(2));
            if(cashier!=null){
                empName=cashier.getCashierName();
            }else{
                Employee employee = new EmployeeController().getEmployee(rst.getString(2));
                empName=employee.getEmployeeName();
            }
            allList.add( new SalaryDetail(rst.getString(2),empName,rst.getString(1),
                    rst.getString(3),rst.getInt(4),rst.getDouble(5),rst.getDouble(6)));
        }
        return allList;
    }
}
