package view.tm;

import javafx.scene.control.Button;

public class EmployeeTM {
    private String empID;
    private String empName;
    private String empNIC;
    private String empAddress;
    private String contact;
    private String status;
    private Button update;
    private Button remove;

    public EmployeeTM() {
    }

    public EmployeeTM(String empID, String empName, String empNIC, String empAddress, String contact, String status, Button update, Button remove) {
        this.empID = empID;
        this.empName = empName;
        this.empNIC = empNIC;
        this.empAddress = empAddress;
        this.contact = contact;
        this.status = status;
        this.update = update;
        this.remove = remove;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNIC() {
        return empNIC;
    }

    public void setEmpNIC(String empNIC) {
        this.empNIC = empNIC;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
