package model;

public class Attendance {
    private String attID;
    private String employeeID;
    private String inTime;
    private String outTime;
    private String date;

    public Attendance() {
    }

    public Attendance(String attID, String employeeID, String inTime, String outTime, String date) {
        this.attID = attID;
        this.employeeID = employeeID;
        this.inTime = inTime;
        this.outTime = outTime;
        this.date = date;
    }

    public String getAttID() {
        return attID;
    }

    public void setAttID(String attID) {
        this.attID = attID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
