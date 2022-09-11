package controller.model_Controller;

import db.DbConnection;
import model.Cashier;
import model.Employee;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeController {
    public boolean saveCashier(Cashier c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Cashier VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, c.getCashierID());
        stm.setObject(2, c.getCashierName());
        stm.setObject(3, c.getCashierNIC());
        stm.setObject(4, c.getCashierAddress());
        stm.setObject(5, c.getContact());
        stm.setObject(6, c.getPassword());
        return stm.executeUpdate() > 0;
    }
    public boolean saveEmployee(Employee e) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Employee VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, e.getEmployeeID());
        stm.setObject(2, e.getEmployeeName());
        stm.setObject(3, e.getEmployeeNIC());
        stm.setObject(4, e.getEmployeeAddress());
        stm.setObject(5, e.getContact());
        stm.setObject(6, e.getState());
        return stm.executeUpdate() > 0;
    }

    public Cashier getCashier(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Cashier WHERE cashierID LIKE '%"+id+"%'");

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Cashier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }
    }

    public ArrayList<Cashier> getAllCashier() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Cashier").executeQuery();
        ArrayList<Cashier> cashierList = new ArrayList<>();
        while (rst.next()) {
            cashierList.add(new Cashier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return cashierList;
    }

    public ArrayList<Employee> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee").executeQuery();
        ArrayList<Employee> empList = new ArrayList<>();
        while (rst.next()) {
            empList.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return empList;
    }

    public Employee getEmployee(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee WHERE employeeID LIKE '%"+id+"%'");

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        } else {
            return null;
        }
    }

    public String getCashierID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT cashierID FROM Cashier ORDER BY cashierID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("H")[1]);
            tempID = tempID + 1;
            if (tempID < 9) {
                return "CH00" + tempID;
            } else if (tempID < 99) {
                return "CH0" + tempID;
            } else {
                return "CH" + tempID;
            }
        } else {
            return "CH001";
        }
    }

    public String getEmpID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT employeeID FROM Employee ORDER BY employeeID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("E")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "E00" + tempID;
            } else if (tempID <= 99) {
                return "E0" + tempID;
            } else {
                return "E" + tempID;
            }
        } else {
            return "E001";
        }
    }

    public boolean updateCashier(Cashier cashier) throws SQLException, ClassNotFoundException {
            PreparedStatement stm = DbConnection.getInstance().getConnection().
                    prepareStatement("UPDATE Cashier SET cashierName=?, cashierNIC=?, cashierAddress=?, contact=?, password=? WHERE cashierID=?");
            stm.setObject(1,cashier.getCashierName());
            stm.setObject(2,cashier.getCashierNIC());
            stm.setObject(3,cashier.getCashierAddress());
            stm.setObject(4,cashier.getContact());
            stm.setObject(5,cashier.getPassword());
            stm.setObject(6,cashier.getCashierID());

            return stm.executeUpdate()>0;
    }

    public boolean updateEmp(Employee employee) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Employee SET employeeName=?, employeeNIC=?, employeeAddress=?, contact=?, state=? WHERE employeeID=?");
        stm.setObject(1,employee.getEmployeeName());
        stm.setObject(2,employee.getEmployeeNIC());
        stm.setObject(3,employee.getEmployeeAddress());
        stm.setObject(4,employee.getContact());
        stm.setObject(5,employee.getState());
        stm.setObject(6,employee.getEmployeeID());

        return stm.executeUpdate()>0;
    }

    public boolean removeEmp(String id) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Employee WHERE employeeID='" + id + "'").executeUpdate() > 0;
    }

    public boolean removeCashier(String cashierID) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Cashier WHERE cashierID='" + cashierID + "'").executeUpdate() > 0;
    }

    public ArrayList<Employee> getEmployees(String text) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee WHERE employeeID LIKE '%"+text+"%' || employeeName LIKE '%"+text+"%'|| employeeNIC LIKE '%"+text+"%'").executeQuery();
        ArrayList<Employee> empList = new ArrayList<>();
        while (rst.next()) {
            empList.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return empList;
    }

    public ArrayList<Cashier> getCashiers(String text) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Cashier WHERE cashierID LIKE '%"+text+"%' || cashierName LIKE '%"+text+"%'|| cashierNIC LIKE '%"+text+"%'").executeQuery();
        ArrayList<Cashier> cashierList = new ArrayList<>();
        while (rst.next()) {
            cashierList.add(new Cashier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return cashierList;
    }
}
