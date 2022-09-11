package model;

public class Employee {
    private String employeeID;
    private String employeeName;
    private String employeeNIC;
    private String employeeAddress;
    private String contact;
    private String state;

    public Employee() {
    }

    public Employee(String employeeID, String employeeName, String employeeNIC, String employeeAddress, String contact, String state) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeNIC = employeeNIC;
        this.employeeAddress = employeeAddress;
        this.contact = contact;
        this.state = state;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNIC() {
        return employeeNIC;
    }

    public void setEmployeeNIC(String employeeNIC) {
        this.employeeNIC = employeeNIC;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
