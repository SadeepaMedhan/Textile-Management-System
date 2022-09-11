package view.tm;

public class SalaryTM {
    private String empID;
    private String name;
    private String status;
    private int workDays;
    private double basicSal;
    private double epf;
    private double Salary;

    public SalaryTM() {
    }

    public SalaryTM(String empID, String name, String status, int workDays, double basicSal, double epf, double salary) {
        this.empID = empID;
        this.name = name;
        this.status = status;
        this.workDays = workDays;
        this.basicSal = basicSal;
        this.epf = epf;
        Salary = salary;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getBasicSal() {
        return basicSal;
    }

    public void setBasicSal(double basicSal) {
        this.basicSal = basicSal;
    }

    public double getEpf() {
        return epf;
    }

    public void setEpf(double epf) {
        this.epf = epf;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }
}
