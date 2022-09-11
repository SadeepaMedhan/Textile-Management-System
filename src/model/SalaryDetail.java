package model;

public class SalaryDetail {
    private String empID;
    private String name;
    private String date;
    private String status;
    private int workDays;
    private double epf;
    private double basicSal;

    public SalaryDetail() {
    }

    public SalaryDetail(String empID, String name, String date, String status, int workDays, double epf, double basicSal) {
        this.empID = empID;
        this.name = name;
        this.date = date;
        this.status = status;
        this.workDays = workDays;
        this.epf = epf;
        this.basicSal = basicSal;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWorkDays() {
        return workDays;
    }

    public void setWorkDays(int workDays) {
        this.workDays = workDays;
    }

    public double getEpf() {
        return epf;
    }

    public void setEpf(double epf) {
        this.epf = epf;
    }

    public double getBasicSal() {
        return basicSal;
    }

    public void setBasicSal(double basicSal) {
        this.basicSal = basicSal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
