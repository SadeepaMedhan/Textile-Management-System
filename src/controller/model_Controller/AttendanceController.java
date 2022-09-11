package controller.model_Controller;

import db.DbConnection;
import model.Attendance;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceController {
    public String getAttendNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT attID FROM Attendance ORDER BY attID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempID = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempID = tempID + 1;
            if (tempID <= 9) {
                return "A-00" + tempID;
            } else if (tempID <= 99) {
                return "A-0" + tempID;
            } else {
                return "A-" + tempID;
            }

        } else {
            return "A-001";
        }
    }

    public boolean markAttend(Attendance a) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Attendance VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, a.getAttID());
        stm.setObject(2, a.getEmployeeID());
        stm.setObject(3, a.getInTime());
        stm.setObject(4, a.getOutTime());
        stm.setObject(5, a.getDate());
        return stm.executeUpdate() > 0;
    }

    public ArrayList<Attendance> getTodayAllAttend(String value) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Attendance WHERE date LIKE '%"+value+"%'").executeQuery();
        ArrayList<Attendance> attendances = new ArrayList<>();
        while (rst.next()) {
            attendances.add(new Attendance(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5)
                    )
            );
        }
        return attendances;
    }

    public ArrayList<Attendance> getAllAttend() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Attendance").executeQuery();
        ArrayList<Attendance> attendances = new ArrayList<>();
        while (rst.next()) {
            attendances.add(new Attendance(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5)
                    )
            );
        }
        return attendances;
    }

    public boolean markLeaveTime(String attID, String laveTime) throws SQLException, ClassNotFoundException {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Attendance SET outTime=? WHERE attID=?");
            stm.setObject(1,laveTime);
            stm.setObject(2,attID);

            return stm.executeUpdate()>0;
    }

    public int getWorkDaysCount(String employeeID, int month) throws SQLException, ClassNotFoundException {
        String query = "SELECT date FROM Attendance WHERE employeeID='"+employeeID+"'";
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(query).executeQuery();
        int count=0;
        while (rst.next()) {
            int mn = Integer.parseInt(rst.getString(1).split("-")[1]);
            if (mn==month){
                System.out.println(employeeID+" "+mn);
                count++;
            }
        }
        return count;
    }
}
