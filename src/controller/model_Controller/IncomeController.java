package controller.model_Controller;

import db.DbConnection;
import model.DateGroup;
import model.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeController {
    public boolean setIncome(Income i) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Income VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, getIncomeNo());
        stm.setObject(2, i.getDate());
        stm.setObject(3, i.getInvoiceNo());
        stm.setObject(4, i.getSalaryID());
        stm.setObject(5, i.getDescription());
        stm.setObject(6, i.getBalance());

        return stm.executeUpdate() > 0;
    }

    public String getIncomeNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT recordNo FROM Income ORDER BY recordNo DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "I-000" + tempID;
            } else if (tempID <= 99) {
                return "I-00" + tempID;
            } else if (tempID <= 999) {
                return "I-0" + tempID;
            } else {
                return "I-" + tempID;
            }
        } else {
            return "I-0001";
        }
    }

    public ArrayList<Income> getAllIncomeReports() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Income").executeQuery();
        ArrayList<Income> incomeList = new ArrayList<>();
        while (rst.next()) {
            incomeList.add(new Income(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getDouble(6)
                    )
            );
        }
        return incomeList;
    }

    public DateGroup getAllDays(String month) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("select date, SUM(balance) From income Group By date LIKE '%" + month + "%'").executeQuery();
        if (rst.next()) {
            return new DateGroup(rst.getString(1), rst.getDouble(2));
        }
        return null;
    }

    public ArrayList<DateGroup> getAllDayList() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("select date,SUM(balance) From income Group By date").executeQuery();
        ArrayList<DateGroup> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new DateGroup(rst.getString(1), rst.getDouble(2)));
        }
        return list;
    }

    public ArrayList<String> getAllMonthIncome(String year) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("select SUM(balance) From income Group By date LIKE '%" + year + "%'").executeQuery();
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            list.add(rst.getString(1));
        }
        return list;
    }

    public ArrayList<String> getAllInvoiceNo(String day) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("select invoiceNo from income where date LIKE '%" + day + "%'").executeQuery();
        ArrayList<String> invoiceList = new ArrayList<>();
        while (rst.next()) {
            invoiceList.add(rst.getString(1));
        }
        return invoiceList;
    }

    public ArrayList<String> getAllExpense(String day) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("select salaryID from income where date LIKE '%" + day + "%'").executeQuery();
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            list.add(rst.getString(1));
        }
        return list;
    }

    public double getTotalRevenue() throws SQLException, ClassNotFoundException {
        String query = "SELECT SUM(balance) FROM income";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        if (rst.next()) {
            return rst.getDouble(1);
        }
        return 0;
    }

    public String getDesc(String salID) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("select description from income where salaryID='" + salID + "'").executeQuery();
        if (rst.next()) {
            return rst.getString(1);
        }
        return "Waste";
    }

    public ArrayList<Income> getAllMonthlyIncome(String month, String selectYear) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Income WHERE date LIKE '%" + month + "-" + selectYear + "%'").executeQuery();
        ArrayList<Income> incomeList = new ArrayList<>();
        while (rst.next()) {
            incomeList.add(new Income(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getDouble(6)
                    )
            );
        }
        return incomeList;
    }

    public ArrayList<Income> getAllMonthlyIncome(String day) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Income WHERE date LIKE '%" + day + "%'").executeQuery();
        ArrayList<Income> incomeList = new ArrayList<>();
        while (rst.next()) {
            incomeList.add(new Income(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getDouble(6)
                    )
            );
        }
        return incomeList;
    }

    public double getTotalRevenue(String month, String selectYear) throws SQLException, ClassNotFoundException {
        String query = "SELECT SUM(balance) FROM income WHERE date LIKE '%" + month + "-" + selectYear + "%'";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        if (rst.next()) {
            return rst.getDouble(1);
        }
        return 0;
    }

    public ArrayList<Income> getMonthlyIncome(String day) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Income WHERE date LIKE '%" + day + "%'").executeQuery();
        ArrayList<Income> incomeList = new ArrayList<>();
        while (rst.next()) {
            incomeList.add(new Income(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getDouble(6)
                    )
            );

        }
        return incomeList;
    }
}

